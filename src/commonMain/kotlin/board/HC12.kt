package board

import board.interfaces.Element
import board.interfaces.SerialCommunicator
import message.*

class HC12(
    override val port: Serial.Port, private val rx: Pin, private val tx: Pin, private val baud: Int
) : Element,
    SerialCommunicator {

    override val pins: ArrayList<Pin>
        get() = arrayListOf(rx, tx)

    init {
        /**
         * Tell the arduino to setup the tx and rx ports for a serial connection
         */
        rx.firmata.sendRequest(
            SerialConfigMessage(
                port,
                byteArrayOf(
                    (baud and 0xFF0000).toByte(),
                    (baud and 0x00FF00).toByte(),
                    (baud and 0x0000FF).toByte()
                ),
                pins[0].position.toByte(),
                pins[1].position.toByte()
            )
        )
    }


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