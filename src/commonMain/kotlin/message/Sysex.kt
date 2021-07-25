package message

/**
 * Sysex Commands (128-255 / 0x80-0xFF)
 * @see <a href="http://firmata.org/wiki/Protocol">Firmata protocol</a> to get the full list of messages
 */
@Suppress("unused")
enum class Sysex(private val byte: Byte) {
    SERIAL_DATA(0x60),
    ENCODER_DATA(0x61),
    ANALOG_MAPPING_QUERY(0x69),
    ANALOG_MAPPING_RESPONSE(0x6A),
    CAPABILITY_QUERY(0x6B),
    CAPABILITY_RESPONSE(0x6C),
    PIN_STATE_QUERY(0x6D),
    PIN_STATE_RESPONSE(0x6E),
    EXTENDED_ANALOG(0x6F),
    SERVO_CONFIG(0x70),
    STRING_DATA(0x71),
    STEPPER_DATA(0x72),
    ONEWIRE_DATA(0x73),
    SHIFT_DATA(0x75),
    I2C_REQUEST(0x76),
    I2C_REPLY(0x77),
    I2C_CONFIG(0x78),
    REPORT_FIRMWARE(0x79),
    SAMPLING_INTERVAL(0x7A),
    SCHEDULER_DATA(0x7B),
    NON_REALTIME(0x7E),
    REALTIME(0x7F);

    fun toInt(): Int = byte.toInt();

    infix fun correspondsTo(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        return toInt() == other || get() == other
    }

    fun get(): Byte = byte;
}