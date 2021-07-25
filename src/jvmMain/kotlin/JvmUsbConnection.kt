import connection.Connection
import gnu.io.NRSerialPort
import message.Message


class JvmUsbConnection(port: String = "COM3", baud: Int = 57600) : Connection {
    var serial: NRSerialPort

    init {
        println(
            "Available ports: ${
                NRSerialPort.getAvailableSerialPorts().joinToString { it }
            }"
        )
        serial = NRSerialPort(
            port, baud
        )
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
        Message(*message).print()
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