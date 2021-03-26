package connection


interface Connection {
    fun connect(): Boolean

    fun read(): ByteArray

    fun asyncRead(callback: (ByteArray) -> Unit)

    fun write(message: ByteArray)

    fun close()

    fun isConnected(): Boolean

    fun waitUntilConnected()
}