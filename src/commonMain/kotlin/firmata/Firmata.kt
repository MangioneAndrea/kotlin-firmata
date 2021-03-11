package firmata

class Firmata {
    private val listeners = HashSet<FirmataListener>();

    fun sendRequest(message: Message) {

    }

    fun registerListener(firmataListener: FirmataListener): Boolean {
        return listeners.add(firmataListener)
    }

    private fun dispatchMessage(message: Message){
        listeners.forEach {
            it.onMessageReceived(message)
        }
    }
}