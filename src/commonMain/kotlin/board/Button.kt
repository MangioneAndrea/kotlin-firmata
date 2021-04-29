package board

import board.interfaces.Element
import board.interfaces.Sensor
import firmata.Message

class Button(pin: Pin) : Element, Sensor {
    override val pins: ArrayList<Pin> = arrayListOf(pin)
    private var status: Pin.Status = Pin.Status(0);


    override fun getValue(): Pin.Status {
        return status
    }

    override fun onMessageReceived(message: Message) {
        message.print()
    }

}