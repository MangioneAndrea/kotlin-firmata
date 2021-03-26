package board

import board.interfaces.Element
import board.interfaces.Sensor

class LightSensor(pin: Pin) : Element, Sensor {
    override val pins: ArrayList<Pin> = arrayListOf(pin)

    override fun getValue(): Pin.Status {
        return pins[0].status
    }


}