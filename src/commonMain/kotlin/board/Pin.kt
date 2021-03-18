package board

import board.interfaces.AbstractPin
import firmata.Constants
import firmata.Firmata
import firmata.Message

@Suppress("unused")
class Pin(override var position: Int, private val firmata: Firmata) : AbstractPin(position) {
    var mode: MODE = MODE.UNSET
        set(v) {
            field = v
            firmata.sendRequest(Message(Constants.MIDI_SET_PIN_MODE, this, v.hex))
        }

    var status: Status = Status.LOW
        set(value) {
            when (mode) {
                MODE.INPUT -> firmata.sendRequest(Message(Constants.MIDI_SET_DIGITAL_PIN_VALUE, this, value.digital));
                MODE.OUTPUT -> firmata.sendRequest(Message(Constants.MIDI_SET_DIGITAL_PIN_VALUE, this, value.digital));
                MODE.ANALOG -> firmata.sendRequest(Message(Constants.MIDI_ANALOG_MESSAGE, this, value.analog));
                MODE.PWM -> return;
                MODE.SERVO -> return;
                MODE.UNSET -> return;
            }
            firmata.sendRequest(Message(Constants.MIDI_SET_DIGITAL_PIN_VALUE, this, value.digital))
            field = value
        }

    class Status(private var value: Int) {
        /**
         * digital pin 7 bitmask
         */
        val digital: Byte
            get() = if (value > 127) 1.toByte() else 0

        /**
         * [0]  analog least significant 7 bits
         * [1]  analog most significant 7 bits
         */
        val analog: ByteArray
            get() {
                val n = value.coerceIn(0, 255)
                return byteArrayOf(
                    (n % 128).toByte(),
                    (n / 128).toByte()
                )
            }

        companion object {
            val HIGH = Status(255)
            val LOW = Status(0)
        }
    }

    enum class MODE(val hex: Byte) {
        INPUT(0), OUTPUT(1), ANALOG(2), PWM(3), SERVO(4), UNSET(-1),
    }

}