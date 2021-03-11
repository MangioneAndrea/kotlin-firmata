package firmata

import board.interfaces.Element
import connection.ConnectionProvider

class Firmata {
    private val listeners = HashSet<FirmataListener>();

    init {
        ConnectionProvider.activeConnection?.asyncRead(::dispatchMessage)
    }

    fun sendRequest(message: Message) {
        ConnectionProvider.activeConnection?.write(message)
    }

    fun registerListener(firmataListener: FirmataListener): Boolean {
        return listeners.add(firmataListener)
    }

    private fun dispatchMessage(message: Message) {
        listeners.forEach {
            it.onMessageReceived(message)
        }
    }

    fun attachElement(el: Element) {


    }
}