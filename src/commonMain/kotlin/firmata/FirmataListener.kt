package firmata

import board.interfaces.Element
import message.Message

interface FirmataListener : Element {
    fun onMessageReceived(message: Message);
}