package board

import board.interfaces.Actor
import board.interfaces.Element
import message.Message
import message.Midi
import message.Sysex

@Suppress("unused")
/**
 * @see 'https://github.com/firmata/protocol/blob/master/servos.md'
 */
class Servo(pin: Pin) : Element, Actor {
    override val pins = arrayListOf(pin)
    private val max = Pin.Status(180);
    private val min = Pin.Status(1);


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

    fun config(pulseLSB: IntRange, pulseMSB: IntRange) {
        pins[0].firmata.sendRequest(
            Message(
                Midi.END_SYSEX.get(),
                Sysex.SERVO_CONFIG.get(),
                pulseLSB.first.toByte(),
                pulseMSB.first.toByte(),
                pulseLSB.last.toByte(),
                pulseMSB.last.toByte(),
                Midi.END_SYSEX.get(),
            )
        )
    }
}