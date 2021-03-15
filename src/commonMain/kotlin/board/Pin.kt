package board

import board.interfaces.AbstractPin
import firmata.Constants
import firmata.Firmata
import firmata.Message

@Suppress("unused")
class Pin(override var position: Int, private val firmata: Firmata) : AbstractPin(position) {
    var mode: MODE = MODE.UNSET
        set(v) {
            firmata.sendRequest(Message(Constants.MIDI_SET_PIN_MODE, this, v.hex))
            field = v
        }

    var status: Status = Status.LOW
        set(value) {
            firmata.sendRequest(Message(Constants.MIDI_SET_DIGITAL_PIN_VALUE, this, value.digital))
            field = value
        }

    class Status(private var hex: Byte) {
        val digital: Byte
            get() = if (hex > 127) 1.toByte() else 0

        val analog: Byte
            get() = hex.coerceIn(0, 1.toByte())

        fun normalize() {
            hex = analog
        }

        companion object {
            val HIGH = Status(1.toByte())
            val LOW = Status(0)
        }
    }

    enum class MODE(val hex: Byte) {
        INPUT(0), OUTPUT(1), UNSET(-1)
    }
}