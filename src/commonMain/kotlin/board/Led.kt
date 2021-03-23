package board

import board.interfaces.Actor
import board.interfaces.Element

@Suppress("unused")
class Led(pin: Pin, mode: Pin.MODE = Pin.MODE.OUTPUT) : Element, Actor {
    override val pins: HashSet<Pin> = hashSetOf(pin);
    override val MIN_PINS = 1;
    override val MAX_PINS = 1;

    init {
        pins.forEach {
            it.mode = mode
        }
        Element.assertPins(this);
    }

    fun turnOn() {
        setValue(Pin.Status.HIGH)
    }

    fun turnOff() {
        setValue(Pin.Status.LOW)
    }

    fun setBrightness(value: Float) {
        setValue(Pin.Status((value.coerceIn(0F, 1F) * 255F).toInt()))
    }


    override fun setValue(status: Pin.Status) {
        pins.forEach {
            it.status = status
        }
    }
}