package firmata

import board.interfaces.Element

interface FirmataListener : Element {
    fun onMessageReceived(message: Message);
}