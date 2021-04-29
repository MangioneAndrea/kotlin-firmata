package connection


interface Connection {
    fun connect(): Boolean

    fun setupReadListener(callback: (data: ByteArray) -> Unit);

    fun write(message: ByteArray)

    fun close()

    fun isConnected(): Boolean

    fun waitUntilConnected()

}