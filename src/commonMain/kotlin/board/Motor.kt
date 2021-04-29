package board

import board.interfaces.Actor
import board.interfaces.Element

class Motor(pinA: Pin, pinB: Pin) : Element, Actor {
    override val pins = arrayListOf(pinA, pinB)


    fun turnForward() {
        setValue(Pin.Status.HIGH, Pin.Status.LOW)
    }

    fun turnForward(speed: Pin.Status) {
        setValue(speed, Pin.Status.LOW)
    }

    fun turnBackward() {
        setValue(Pin.Status.LOW, Pin.Status.HIGH)
    }

    fun turnBackward(speed: Pin.Status) {
        setValue(Pin.Status.LOW, speed)
    }

    fun stop() {
        setValue(Pin.Status.LOW, Pin.Status.LOW)
    }

    override fun setValue(vararg status: Pin.Status) {
        pins.forEachIndexed { index, pin ->
            pin.status = status[index]
        }
    }
}
