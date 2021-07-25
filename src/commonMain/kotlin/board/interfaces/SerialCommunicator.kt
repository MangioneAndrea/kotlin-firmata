package board.interfaces

import firmata.FirmataListener
import message.Message
import message.Serial

interface SerialCommunicator : FirmataListener {
    val port: Serial.Port
    fun writeMessage(message: Message);
    fun readMessage(mode: Serial.ReadMode = Serial.ReadMode.SERIAL_READ_MODE_CONTINUOUS);
}