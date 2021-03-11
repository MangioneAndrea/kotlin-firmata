package connection

import firmata.Message

interface Connection {
    fun connect(): Boolean

    fun read(): Message

    fun asyncRead(callback: (Message) -> Unit)

    fun write(message: Message)

    fun close()
}