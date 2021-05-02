package board

import exceptions.ActionNotSupportedWithCurrentModeException
import exceptions.PinModeNotAvailableException
import firmata.*
import message.AnalogMessage
import message.DigitalMessage
import message.PinModeMessage

@Suppress("unused")
class Pin(var position: Int, private val firmata: Firmata, val modes: HashSet<MODE>) {
    var mode: MODE = MODE.OUTPUT
        set(mode) {
            if (!modes.contains(mode)) throw PinModeNotAvailableException(this, mode);
            field = mode
            firmata.sendRequest(PinModeMessage(this, mode))
        }

    var status: Status = Status.LOW
        set(value) {
            field = value
            when (mode) {
                MODE.INPUT, MODE.OUTPUT, MODE.INPUT_PULLUP -> firmata.sendRequest(
                    DigitalMessage(
                        this,
                        value.digital
                    )
                );
                MODE.ANALOG, MODE.PWM, MODE.SERVO -> firmata.sendRequest(
                    AnalogMessage(
                        this,
                        *value.analog
                    )
                );
                else -> throw ActionNotSupportedWithCurrentModeException(this, mode, "setting the value");
            }
        }

    class Status(var value: Int) {
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
        INPUT(0x00),
        OUTPUT(0x01),
        ANALOG(0x02),
        PWM(0x03),
        SERVO(0x04),
        SHIFT(0x05),
        I2C(0x06),
        ONEWIRE(0x07),
        STEPPER(0x08),
        ENCODER(0x09),
        SERIAL(0x0a),
        INPUT_PULLUP(0x0B);

        companion object {
            fun from(hex: Byte): MODE {
                return values().find { it.hex == hex } ?: OUTPUT
            }
        }
    }


    override fun hashCode(): Int {
        return position
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as Pin
        if (position == other.position) return true
        return false
    }
}