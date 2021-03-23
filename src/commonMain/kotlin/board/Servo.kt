package board

import board.interfaces.Actor
import board.interfaces.Element

@Suppress("unused")
class Servo(pin: Pin) : Element, Actor {
    override val pins = hashSetOf(pin);
    override val MIN_PINS = 1;
    override val MAX_PINS = 1;
    private val max = Pin.Status(180);
    private val min = Pin.Status(1);


    init {
        pins.forEach {
            it.mode = Pin.MODE.SERVO
        }
        Element.assertPins(this);
    }

    fun toMax() {
        setValue(max)
    }

    fun toMin() {
        setValue(min)
    }

    override fun setValue(status: Pin.Status) {
        pins.forEach {
            it.status = status
        }
    }
}