package firmata

import board.Pin

data class Message(val type: Byte, val pin: Pin?, val content: Byte) {

    constructor(bytes: ByteArray) : this(bytes[0], null, bytes[2])


    fun asValidFirmataMessage(): ByteArray {
        return byteArrayOf(
            type,
            (pin!!.position and 0x7F).toByte(),
            content
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        other as Message
        if (content != other) return false
        return true
    }

    override fun hashCode(): Int {
        return content.hashCode()
    }
}