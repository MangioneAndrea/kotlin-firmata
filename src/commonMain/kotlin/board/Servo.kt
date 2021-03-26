package board

import board.interfaces.Actor
import board.interfaces.Element

@Suppress("unused")
class Servo(pin: Pin) : Element, Actor {
    override val pins = arrayListOf(pin)
    private val max = Pin.Status(180);
    private val min = Pin.Status(1);


    init {
        pins[0].mode = Pin.MODE.SERVO
    }

    fun toMax() {
        setValue(max)
    }

    fun toMin() {
        setValue(min)
    }

    fun to(value: Int) {
        setValue(Pin.Status(value.coerceIn(0, 180)))
    }

    fun to(value: Float) {
        setValue(Pin.Status((value.coerceIn(0F, 1F) * 180F).toInt()))
    }

    fun toRight(amount: Int = 3) {
        to(pins[0].status.value + amount)
    }

    fun toLeft(amount: Int = 3) {
        to(pins[0].status.value - amount)
    }

    override fun setValue(vararg status: Pin.Status) {
        pins[0].status = status[0]
    }
}