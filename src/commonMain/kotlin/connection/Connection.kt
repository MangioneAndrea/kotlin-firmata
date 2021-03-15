package connection

import firmata.Message

interface Connection {
    fun connect(): Boolean

    fun read(): ByteArray

    fun asyncRead(callback: (ByteArray) -> Unit)

    fun write(message: ByteArray)

    fun close()
}