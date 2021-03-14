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
        connection.write(message)
    }

    fun registerListener(firmataListener: FirmataListener): Boolean {
        return listeners.add(firmataListener)
    }

    private fun dispatchMessage(message: Message) {
        listeners.forEach {
            it.onMessageReceived(message)
        }
    }

    companion object {
        private fun adaptedPins(pins: Array<out AbstractPin>, firmata: Firmata): Array<Pin> {
            return pins.map {
                Pin(it.position, firmata)
            }.toTypedArray()
        }

        fun Firmata.Led(vararg pins: AbstractPin): board.Led {
            return board.Led(*adaptedPins(pins, this))
        }

        fun Firmata.HC12(vararg pins: AbstractPin): board.HC12 {
            return board.HC12(*adaptedPins(pins, this))
        }
    }
}

