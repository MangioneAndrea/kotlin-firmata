package firmata

interface FirmataListener {
    fun onMessageReceived(message: Message);
}