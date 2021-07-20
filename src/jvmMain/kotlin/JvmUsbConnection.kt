import connection.Connection
import gnu.io.NRSerialPort


class JvmUsbConnection : Connection {
    var serial: NRSerialPort

    init {
        NRSerialPort.getAvailableSerialPorts().forEach { println(it) }
        serial = NRSerialPort("COM3", 57600)
    }

    override fun connect(): Boolean {
        return serial.connect()
    }

    override fun setupReadListener(callback: (data: ByteArray) -> Unit) {
        Thread {
            while (serial.isConnected) {
                try {
                    val n = serial.inputStream.available()
                    if (n > 0) {
                        val bytes = serial.inputStream.readNBytes(serial.inputStream.available())
                        callback(bytes)
                    } else {
                        Thread.sleep(10);
                    }
                } catch (e: Error) {
                    print(e)
                }
            }
        }.start()
    }

    override fun write(message: ByteArray) {
        serial.outputStream.write(message)
        serial.outputStream.flush()
    }

    override fun close() {
        serial.disconnect()
    }

    override fun isConnected(): Boolean {
        return serial.isConnected
    }

    override fun waitUntilConnected() {
        while (!isConnected()) {
            println("waiting")
            Thread.sleep(100);
        }
    }

}