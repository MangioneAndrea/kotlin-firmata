package firmata

import board.Pin
import board.boards.Board
import connection.Connection
import exceptions.PinNotOnBoardException
import firmata.Constants.*
import java.lang.NullPointerException

class Firmata(private val connection: Connection) {
    private val listeners = HashSet<FirmataListener>();
    private val messageBuffer = MessageBuffer(::deliverMessage)
    lateinit var selectedBoard: Board;

    init {
        connection.run {
            waitUntilConnected()
            setupReadListener(messageBuffer::addBytes)
            sendRequest(CapabilityQueryMessage());
        }
    }

    private fun updateCapability(message: Message) {
        val pinSetup = message.splitAll(0x7F)
        selectedBoard = Board(pinSetup.size);
        pinSetup.forEachIndexed { index, msg ->

            selectedBoard.setPin(index, Pin(index, this, *msg.content.asList().chunked(2).map {
                Pin.MODE.from(it[0])
            }.toHashSet()))
        }
    }

    fun sendRequest(message: Message) {
        connection.write(message.content)
    }


    fun registerListener(firmataListener: FirmataListener): Boolean {
        return listeners.add(firmataListener)
    }


    //https://github.com/firmata/protocol/blob/master/protocol.md#message-types
    private fun deliverMessage(message: Message) {
        println("message delivered: ${message.toHex()}")
        if (message.isSysex()) {
            val sysexMessage = message.getSysexContent()
            if (message[1] == SYSEX_CAPABILITY_RESPONSE.get()) {
                updateCapability(sysexMessage startingAt 1)
            }
        } else {
            when (message.firstNibble()) {
                0xE0.toByte(), 0xC0.toByte() -> {
                    // Analog | Second bit = pin
                    listeners.forEach {
                        if (it.pins.any { pin -> pin.position.toByte() == message.secondNibble() }) it.onMessageReceived(
                            message
                        )
                    }
                }
                0x90.toByte(), 0xD0.toByte() -> {
                    // Digital | Second bit = port
                }
                0xF0.toByte() -> {
                    // Set | Second byte = pin / system reset
                    if (message.firstByte() == MIDI_SYSTEM_RESET.get()) {
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
            selectedBoard[pin]!!.mode = mode
            return selectedBoard[pin]!!
        } catch (e: Exception) {
            println(e)
            when (e) {
                is ArrayIndexOutOfBoundsException -> throw PinNotOnBoardException(pin)
                else -> throw e;
            }
        }
    }

    @Suppress("unused")
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
    }
}

