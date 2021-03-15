package firmata

import board.Pin
import board.interfaces.AbstractPin
import connection.Connection

class Firmata(private val connection: Connection) {
    private val listeners = HashSet<FirmataListener>();

    init {
        connection.asyncRead(::dispatchMessage)
    }

    fun sendRequest(message: Message) {
        connection.write(message.asValidFirmataMessage())
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
            return board.Led(Pin(pin,this))
        }

    }
}

