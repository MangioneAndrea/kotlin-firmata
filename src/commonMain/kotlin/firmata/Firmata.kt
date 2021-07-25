package firmata

import board.Pin
import board.boards.Board
import board.interfaces.SerialCommunicator
import connection.Connection
import exceptions.PinNotOnBoardException
import message.*

class Firmata(private val connection: Connection) {
    private val listeners = HashSet<FirmataListener>();
    private val messageBuffer = MessageBuffer(::deliverMessage)
    var selectedBoard: Board? = null;

    init {
        connection.run {
            connect()
            waitUntilConnected()
            setupReadListener(messageBuffer::addBytes)
            sendRequest(CapabilityQueryMessage());
        }
    }

    private fun updateCapability(message: Message) {
        val pinSetup = message.splitAll(0x7F)
        val board = Board(pinSetup.size);
        pinSetup.forEachIndexed { index, msg ->

            board.setPin(index, Pin(index, this, *msg.content.asList().chunked(2).map {
                Pin.MODE.from(it[0])
            }.toHashSet()))
        }
        println("board initialized")
        selectedBoard = board
    }

    fun sendRequest(message: Message) {
        connection.write(message.content)
    }


    fun registerListener(firmataListener: FirmataListener): Boolean {
        return listeners.add(firmataListener)
    }

    fun awaitInitialisation() {
        while (selectedBoard == null) {
            Thread.sleep(10)
        }
    }



    //https://github.com/firmata/protocol/blob/master/protocol.md#message-types
    private fun deliverMessage(message: Message) {
        println("message delivered: ${message.asHexString()}")

        if (message.isSysex()) {
            val sysexMessage = message.sysexContent
            if (Sysex.CAPABILITY_RESPONSE correspondsTo message[1]) {
                updateCapability(sysexMessage startingAt 1)
            }
            if (Sysex.REPORT_FIRMWARE correspondsTo message[1]) {
                println("Firmware: ${message.sysexContent.stringMessage}")
            }
            if (Sysex.SERIAL_DATA correspondsTo message[1]) {
                val command = Message(message[2])
                if (Serial.SERIAL_REPLY correspondsTo (command.firstNibble)) {
                    listeners.forEach {
                        if (it is SerialCommunicator) {
                            if (it.port correspondsTo command.secondNibble) {
                                it.onMessageReceived(message)
                            }
                        }
                        if (it.pins.any { pin -> pin.position.toByte() == message.secondNibble }) it.onMessageReceived(
                            message
                        )
                    }
                }
            }
        } else {
            when (message.firstNibble) {
                0xE0.toByte(), 0xC0.toByte() -> {
                    // Analog | Second bit = pin
                    listeners.forEach {
                        if (it.pins.any { pin -> pin.position.toByte() == message.secondNibble }) it.onMessageReceived(
                            message
                        )
                    }
                }
                0x90.toByte(), 0xD0.toByte() -> {
                    // Digital | Second bit = port
                }
                0xF0.toByte() -> {
                    // Set | Second byte = pin / system reset
                    if (Midi.SYSTEM_RESET correspondsTo message.firstByte) {
                        // System reset
                    } else {
                        listeners.forEach {
                            if (it.pins.any { pin -> pin.position == message[1].toInt() }) it.onMessageReceived(message)
                        }
                    }
                }
            }
        }
    }

    private fun requestPin(pin: Int, mode: Pin.MODE): Pin {
        try {
            selectedBoard!![pin]!!.mode = mode
            return selectedBoard!![pin]!!
        } catch (e: Exception) {
            when (e) {
                is ArrayIndexOutOfBoundsException -> throw PinNotOnBoardException(pin)
                else -> throw e;
            }
        }
    }

    @Suppress("unused", "functionName")
    companion object {
        fun Firmata.Led(pin: Int): board.Led {
            return board.Led(requestPin(pin, Pin.MODE.OUTPUT))
        }

        fun Firmata.PWMLed(pin: Int): board.Led {
            return board.Led(requestPin(pin, Pin.MODE.PWM))
        }

        fun Firmata.Servo(pin: Int): board.Servo {
            return board.Servo(requestPin(pin, Pin.MODE.SERVO))
        }

        fun Firmata.Motor(pin1: Int, pin2: Int): board.Motor {
            return board.Motor(requestPin(pin1, Pin.MODE.OUTPUT), requestPin(pin2, Pin.MODE.OUTPUT))
        }

        fun Firmata.PWMMotor(pin1: Int, pin2: Int): board.Motor {
            return board.Motor(requestPin(pin1, Pin.MODE.PWM), requestPin(pin2, Pin.MODE.PWM))
        }

        fun Firmata.Photoresistor(pin: Int): board.Photoresistor {
            val element = board.Photoresistor(requestPin(pin, Pin.MODE.PWM))
            registerListener(element)
            return element
        }

        fun Firmata.Button(pin: Int): board.Button {
            val element = board.Button(requestPin(pin, Pin.MODE.INPUT))
            registerListener(element)
            return element
        }

        fun Firmata.HC12(rx: Int, tx: Int, baud: Int = 9600): board.HC12 {
            val element = board.HC12(
                Serial.Port.HW_SERIAL0,
                requestPin(rx, Pin.MODE.INPUT_PULLUP),
                requestPin(tx, Pin.MODE.OUTPUT)
            )
            this.sendRequest(
                SerialConfigMessage(
                    Serial.Port.HW_SERIAL0,
                    byteArrayOf(
                        (baud and 0xFF0000).toByte(),
                        (baud and 0x00FF00).toByte(),
                        (baud and 0x0000FF).toByte()
                    ),
                    element.pins[0].position.toByte(),
                    element.pins[1].position.toByte()
                )
            )
            registerListener(element)
            return element
        }
    }
}

