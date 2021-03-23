package firmata

import board.Pin
import connection.Connection

class Firmata(private val connection: Connection) {
    private val listeners = HashSet<FirmataListener>();

    init {
        connection.asyncRead(::dispatchMessage)
    }

    fun sendRequest(message: Message) {
        connection.write(message.asValidFirmataMessage())
    }

    fun sendRequest(message: ByteArray) {
        connection.write(message)
    }

    fun registerListener(firmataListener: FirmataListener): Boolean {
        return listeners.add(firmataListener)
    }

    private fun dispatchMessage(message: ByteArray) {
        listeners.forEach {
            it.onMessageReceived(Message(message))
        }
    }

    companion object {
        fun Firmata.Led(pin: Int): board.Led {
            return board.Led(Pin(pin, this), Pin.MODE.OUTPUT)
        }

        fun Firmata.PWMLed(pin: Int): board.Led {
            return board.Led(Pin(pin, this), Pin.MODE.PWM)
        }

        fun Firmata.Servo(pin: Int): board.Servo {
            return board.Servo(Pin(pin, this))
        }
    }
}

