package firmata

@Suppress("unused")
/**
 * Midi Commands (128-255 / 0x80-0xFF)
 * @see <a href="http://firmata.org/wiki/Protocol">Firmata protocol</a> to get the full list of messages
 */
enum class Constants(val byte: Byte) {
    MIDI_DIGITAL_MESSAGE(0x90.toByte()),
    MIDI_ANALOG_MESSAGE(0xE0.toByte()),
    MIDI_REPORT_ANALOG(0xC0.toByte()),
    MIDI_REPORT_DIGITAL(0xD0.toByte()),
    MIDI_SET_PIN_MODE(0xF4.toByte()),
    MIDI_SET_DIGITAL_PIN_VALUE(0xF5.toByte()),
    MIDI_REPORT_VERSION(0xF9.toByte()),
    MIDI_SYSTEM_RESET(0xFF.toByte()),
    MIDI_START_SYSEX(0xF0.toByte()),
    MIDI_END_SYSEX(0xF7.toByte()),
    SYSEX_SERIAL_DATA(0x60),
    SYSEX_ENCODER_DATA(0x61),
    SYSEX_SERVO_CONFIG(0x70),
    SYSEX_STRING_DATA(0x71),
    SYSEX_STEPPER_DATA(0x72),
    SYSEX_ONEWIRE_DATA(0x73),
    SYSEX_SHIFT_DATA(0x75),
    SYSEX_I2C_REQUEST(0x76),
    SYSEX_I2C_REPLY(0x77),
    SYSEX_I2C_CONFIG(0x78),
    SYSEX_REPORT_FIRMWARE(0x79),
    SYSEX_EXTENDED_ANALOG(0x6F),
    SYSEX_PIN_STATE_QUERY(0x6D),
    SYSEX_PIN_STATE_RESPONSE(0x6E),
    SYSEX_CAPABILITY_QUERY(0x6B),
    SYSEX_CAPABILITY_RESPONSE(0x6C),
    SYSEX_ANALOG_MAPPING_QUERY(0x69),
    SYSEX_ANALOG_MAPPING_RESPONSE(0x6A),
    SYSEX_SAMPLING_INTERVAL(0x7A),
    SYSEX_SCHEDULER_DATA(0x7B),
    SYSEX_NON_REALTIME(0x7E),
    SYSEX_REALTIME(0x7F),
    PIN_MODE_INPUT(0x00),
    PIN_MODE_OUTPUT(0x01),
    PIN_MODE_ANALOG(0x02),
    PIN_MODE_PWM(0x03),
    PIN_MODE_SERVO(0x04),
    PIN_MODE_SHIFT(0x05),
    PIN_MODE_I2C(0x06),
    PIN_MODE_ONEWIRE(0x07),
    PIN_MODE_STEPPER(0x08),
    PIN_MODE_ENCODER(0x09),
    PIN_MODE_SERIAL(0x0A),
    PIN_MODE_PULLUP(0x0B),
    PIN_MODE_IGNORE(0x7F);

    fun toInt(): Int {
        return byte.toInt()
    }

    fun get(): Byte {
        return byte;
    }
}