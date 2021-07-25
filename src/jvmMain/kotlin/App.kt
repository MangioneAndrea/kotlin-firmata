import firmata.Firmata
import firmata.Firmata.Companion.HC12
import firmata.Firmata.Companion.PWMLed
import message.CapabilityQueryMessage
import message.Message


class App {

    companion object {

        fun loop(firmata: Firmata) {
            println(firmata.selectedBoard!!.capabilities)
            val hc12 = firmata.HC12(11, 10, 9600)
            val led = firmata.PWMLed(5);

            while (true) {
                // Write hello
                Thread.sleep(10);
                hc12.writeMessage(Message(0x48, 0x65, 0x6c, 0x6c, 0x6f))
                //led.setBrightness(Math.random().toFloat())
            }
        }

        fun setup(firmata: Firmata) {
        }


        @JvmStatic
        fun main(args: Array<String>) {
            val connection = JvmUsbConnection()
            val uno = Firmata(connection)
            Thread.sleep(2000)
            uno.sendRequest(CapabilityQueryMessage());
            uno.awaitInitialisation()
            setup(uno);
            loop(uno)


        }

        fun directHC12() {
            val connection = JvmUsbConnection("COM4", 9600)
            val hc12 = Firmata(connection)

            while (true) {
                // Write hello
                Thread.sleep(10);
                hc12.sendRequest(Message(0x48, 0x65, 0x6c, 0x6c, 0x6f))
                //led.setBrightness(Math.random().toFloat())
            }
        }
    }
}
