package board

import board.interfaces.AbstractPin
import firmata.Firmata
import firmata.Message

@Suppress("unused")
class Pin(override var position: Int, private val firmata: Firmata) : AbstractPin(position) {
    var status: Status = Status.LOW
        set(value) {
            firmata.sendRequest(Message(ByteArray(value.analog)))
            field = value
        }

    class Status(private var hex: Int) {
        val digital: Int
            get() = if (hex > 127) 255 else 0

        val analog: Int
            get() = hex.coerceIn(0, 255)

        fun normalize() {
            hex = analog
        }

        companion object {
            val HIGH = Status(255)
            val LOW = Status(0)
        }
    }
}