package board

import board.interfaces.Actor
import board.interfaces.Element
import board.interfaces.Sensor

class HC12 : Element, Sensor, Actor {
    override val pins: ArrayList<Pin>
        get() = TODO("Not yet implemented")

    override fun setValue(vararg status: Pin.Status) {
        TODO("Not yet implemented")
    }

    override fun getValue(): Pin.Status {
        TODO("Not yet implemented")
    }


}