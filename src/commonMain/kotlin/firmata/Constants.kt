package firmata

@Suppress("unused")
/**
 * Midi Commands (128-255 / 0x80-0xFF)
 * @see <a href="http://firmata.org/wiki/Protocol">Firmata protocol</a> to get the full list of messages
 */
object Constants {
    const val MIDI_DIGITAL_MESSAGE = 0x90.toByte()

    const val MIDI_ANALOG_MESSAGE = 0xE0.toByte()

    const val MIDI_REPORT_ANALOG = 0xC0.toByte()

    const val MIDI_REPORT_DIGITAL = 0xD0.toByte()

    const val MIDI_SET_PIN_MODE = 0xF4.toByte()

    const val MIDI_SET_DIGITAL_PIN_VALUE = 0xF5.toByte()

    const val MIDI_REPORT_VERSION = 0xF9.toByte()

    const val MIDI_SYSTEM_RESET = 0xFF.toByte()

    const val MIDI_START_SYSEX = 0xF0.toByte()

    const val MIDI_END_SYSEX = 0xF7.toByte()

    const val SYSEX_SERIAL_DATA = 0x60.toByte()

    const val SYSEX_ENCODER_DATA = 0x61.toByte()

    const val SYSEX_SERVO_CONFIG = 0x70.toByte()

    const val SYSEX_STRING_DATA = 0x71.toByte()

    const val SYSEX_STEPPER_DATA = 0x72.toByte()

    const val SYSEX_ONEWIRE_DATA = 0x73.toByte()

    const val SYSEX_SHIFT_DATA = 0x75.toByte()

    const val SYSEX_I2C_REQUEST = 0x76.toByte()

    const val SYSEX_I2C_REPLY = 0x77.toByte()

    const val SYSEX_I2C_CONFIG = 0x78.toByte()

    const val SYSEX_REPORT_FIRMWARE = 0x79.toByte()

    const val SYSEX_EXTENDED_ANALOG = 0x6F.toByte()

    const val SYSEX_PIN_STATE_QUERY = 0x6D.toByte()

    const val SYSEX_PIN_STATE_RESPONSE = 0x6E.toByte()

    const val SYSEX_CAPABILITY_QUERY = 0x6B.toByte()

    const val SYSEX_CAPABILITY_RESPONSE = 0x6C.toByte()

    const val SYSEX_ANALOG_MAPPING_QUERY = 0x69.toByte()

    const val SYSEX_ANALOG_MAPPING_RESPONSE = 0x6A.toByte()

    const val SYSEX_SAMPLING_INTERVAL = 0x7A.toByte()

    const val SYSEX_SCHEDULER_DATA = 0x7B.toByte()

    const val SYSEX_NON_REALTIME = 0x7E.toByte()

    const val SYSEX_REALTIME = 0x7F.toByte()

    const val PIN_MODE_INPUT = 0x00

    const val PIN_MODE_OUTPUT = 0x01

    const val PIN_MODE_ANALOG = 0x02

    const val PIN_MODE_PWM = 0x03

    const val PIN_MODE_SERVO = 0x04

    const val PIN_MODE_SHIFT = 0x05

    const val PIN_MODE_I2C = 0x06

    const val PIN_MODE_ONEWIRE = 0x07

    const val PIN_MODE_STEPPER = 0x08

    const val PIN_MODE_ENCODER = 0x09

    const val PIN_MODE_SERIAL = 0x0A

    const val PIN_MODE_PULLUP = 0x0B

    const val PIN_MODE_IGNORE = 0x7F
}