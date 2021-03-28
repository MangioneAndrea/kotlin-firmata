package board

import board.interfaces.AbstractPin
import firmata.*

@Suppress("unused")
class Pin(override var position: Int, private val firmata: Firmata) : AbstractPin(position) {
    var mode: MODE = MODE.UNSET
        set(mode) {
            field = mode
            firmata.sendRequest(PinModeMessage(this, mode))
        }

    var status: Status = Status.LOW
        set(value) {
            field = value
            when (mode) {
                MODE.INPUT, MODE.OUTPUT -> firmata.sendRequest(
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
                MODE.UNSET -> return;
            }
        }
        get() {
            when (mode) {
                MODE.INPUT, MODE.OUTPUT -> {
                    firmata.sendRequest(
                        DigitalReportMessageEnable(this)
                    )
                    firmata.readValue()
                    firmata.sendRequest(
                        DigitalReportMessageDisable(this)
                    )
                }
                MODE.ANALOG, MODE.PWM, MODE.SERVO -> {
                    firmata.sendRequest(
                        AnalogReportMessageEnable(this)
                    );
                    firmata.readValue()
                    firmata.sendRequest(
                        AnalogReportMessageDisable(this)
                    );
                }
                MODE.UNSET -> return Status(0);
            }

            return Status(0)
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
        INPUT(0), OUTPUT(1), ANALOG(2), PWM(3), SERVO(4), UNSET(-1),
    }

}