package firmata

data class Message(var content: ByteArray) {
    fun toInt(): Int {
        return 0;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        other as Message
        if (!content.contentEquals(other.content)) return false
        return true
    }

    override fun hashCode(): Int {
        return content.contentHashCode()
    }
}