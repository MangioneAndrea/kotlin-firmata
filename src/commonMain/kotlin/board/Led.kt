package board

import board.interfaces.Actor
import board.interfaces.Element
import firmata.Message

@Suppress("unused")
class Led(vararg pins: Pin) : Element, Actor {
    override val pins: HashSet<Pin> = pins.toCollection(HashSet());
    override val MIN_PINS = 1;
    override val MAX_PINS = 1;

    init {
        pins.forEach {
            it.mode = Pin.MODE.OUTPUT
        }
        Element.assertPins(this);
    }

    fun turnOn() {
        setValue(Pin.Status.HIGH)
    }

    fun turnOff() {
        setValue(Pin.Status.LOW)
    }


    override fun setValue(status: Pin.Status) {
        pins.forEach {
            it.status = status
        }
    }
}