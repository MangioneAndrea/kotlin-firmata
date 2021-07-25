package board

import board.interfaces.Element
import board.interfaces.SerialCommunicator
import message.Message
import message.Serial
import message.SerialReadMessage
import message.SerialWriteMessage

class HC12(
    override val port: Serial.Port, private val rx: Pin, private val tx: Pin
) : Element,
    SerialCommunicator {
    override val pins: ArrayList<Pin>
        get() = arrayListOf(rx, tx)

    override fun writeMessage(message: Message) {
        rx.firmata.sendRequest(SerialWriteMessage(port, *message.content))
    }

    override fun readMessage(mode: Serial.ReadMode) {
        SerialReadMessage(port, mode)
    }

    override fun onMessageReceived(message: Message) {
        message.sysexContent startingAt 2
    }
}