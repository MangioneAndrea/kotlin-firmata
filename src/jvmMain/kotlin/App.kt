import firmata.Firmata
import firmata.Firmata.Companion.Led
import message.CapabilityQueryMessage


class App {

    companion object {

        fun blink(firmata: Firmata) {
            val led = firmata.Led(7);
            while (true) {
                Thread.sleep(1000)

                led.turnOn();
                println("Turned on!")
                Thread.sleep(1000)
                led.turnOff()
            }
        }


        @JvmStatic
        fun main(args: Array<String>) {
            val connection = JvmUsbConnection()
            val firmata = Firmata(connection)
            Thread.sleep(2000)
            firmata.sendRequest(CapabilityQueryMessage());
            firmata.awaitInitialisation()
            blink(firmata)
        }
    }
}
