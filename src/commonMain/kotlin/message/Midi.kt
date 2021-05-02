package message

/**
 * Midi Commands (128-255 / 0x80-0xFF)
 * @see <a href="http://firmata.org/wiki/Protocol">Firmata protocol</a> to get the full list of messages
 */
enum class Midi(val byte: Byte) {
    DIGITAL_MESSAGE(0x90.toByte()),
    ANALOG_MESSAGE(0xE0.toByte()),
    REPORT_ANALOG(0xC0.toByte()),
    REPORT_DIGITAL(0xD0.toByte()),
    SET_PIN_MODE(0xF4.toByte()),
    SET_DIGITAL_PIN_VALUE(0xF5.toByte()),
    REPORT_VERSION(0xF9.toByte()),
    SYSTEM_RESET(0xFF.toByte()),
    START_SYSEX(0xF0.toByte()),
    END_SYSEX(0xF7.toByte());

    fun toInt(): Int {
        return byte.toInt()
    }

    infix fun correspondsTo(other: Any): Boolean {
        return toInt() == other || get() == other;
    }

    fun get(): Byte {
        return byte;
    }
}