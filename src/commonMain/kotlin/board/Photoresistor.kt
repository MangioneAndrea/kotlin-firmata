package board

import board.interfaces.Element
import board.interfaces.Sensor

class Photoresistor(pin: Pin) : Element, Sensor {
    override val pins: ArrayList<Pin> = arrayListOf(pin)

    init {
        pins[0].mode=Pin.MODE.ANALOG
    }



    override fun getValue(): Pin.Status {
        return pins[0].status
    }


}