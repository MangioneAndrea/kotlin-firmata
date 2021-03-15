package board

import board.interfaces.Actor
import board.interfaces.Element
import board.interfaces.Sensor

class HC12 : Element, Sensor, Actor {
    override val pins: HashSet<Pin>
    override val MIN_PINS = 2;
    override val MAX_PINS = 3;

    override fun setValue(status: Pin.Status) {
        TODO("Not yet implemented")
    }

    override fun getValue(): Pin.Status {
        TODO("Not yet implemented")
    }


    constructor(vararg pins: Pin) {
        this.pins = pins.toCollection(HashSet())
        Element.assertPins(this);
    }
}