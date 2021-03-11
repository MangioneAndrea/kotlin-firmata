package board

import board.interfaces.Actor
import board.interfaces.Element

class Led : Element, Actor {
    override val pins: HashSet<Pin>;
    override var MIN_PINS = 1;
    override var MAX_PINS = 1;

    fun turnOn() {
        pins.forEach {
            it.setStatus(Pin.Status.HIGH)
        }
    }

    constructor(vararg pins: Pin) {
        Element.assertPins(this);
        this.pins = pins.toCollection(HashSet())
    }
}