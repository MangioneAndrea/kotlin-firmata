import connection.Connection

class MockupConnection(val onWrite: (data: ByteArray) -> Unit) : Connection {
    lateinit var feedData: (data: ByteArray) -> Unit;

    override fun connect(): Boolean {
        return true;
    }


    override fun setupReadListener(callback: (data: ByteArray) -> Unit) {
        this.feedData = callback;
    }

    override fun write(message: ByteArray) {
        this.onWrite(message)
    }

    override fun close() {
        return;
    }

    override fun isConnected(): Boolean {
        return true;
    }

    override fun waitUntilConnected() {
        return;
    }
}