package board

import board.interfaces.Actor
import board.interfaces.Element

@Suppress("unused")
class Led(pin: Pin, mode: Pin.MODE = Pin.MODE.OUTPUT) : Element, Actor {

    override val pins = arrayListOf(pin)

    init {
        pins[0].mode = mode
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


    override fun setValue(vararg status: Pin.Status) {
        pins[0].status = status[0]
    }
}