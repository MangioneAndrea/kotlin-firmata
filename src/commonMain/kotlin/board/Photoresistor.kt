package board

import board.interfaces.Element
import board.interfaces.Sensor
import message.Message

class Photoresistor(pin: Pin) : Element, Sensor {
    override val pins: ArrayList<Pin> = arrayListOf(pin)


    override fun getValue(): Pin.Status {
        return pins[0].status
    }

    override fun onMessageReceived(message: Message) {
        TODO("Not yet implemented")
    }

}