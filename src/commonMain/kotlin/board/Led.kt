package board

import board.interfaces.Actor
import board.interfaces.Element
import firmata.Message

@Suppress("unused")
class Led : Element, Actor {
    override val pins: HashSet<Pin>;
    override val MIN_PINS = 1;
    override val MAX_PINS = 1;

    fun turnOn() {
        setValue(Pin.Status.HIGH)
    }

    fun turnOff() {
        setValue(Pin.Status.LOW)
    }

    constructor(vararg pins: Pin) {
        this.pins = pins.toCollection(HashSet())
        pins.forEach {
            it.mode = Pin.MODE.OUTPUT
        }
        Element.assertPins(this);
    }

    override fun setValue(status: Pin.Status) {
        pins.forEach {
            it.status = status
        }
    }
}