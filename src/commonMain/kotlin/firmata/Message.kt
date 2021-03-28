package firmata

import board.Pin

open class Message(vararg var content: Byte) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        other as Message
        return content.contentEquals(other.content);
    }

    override fun hashCode(): Int {
        return content.contentHashCode()
    }
}

/** analog 14-bit data format
 * 0  analog pin, 0xE0-0xEF, (MIDI Pitch Wheel)
 * 1  analog least significant 7 bits
 * 2  analog most significant 7 bits
 */
class AnalogMessage(pin: Pin, vararg content: Byte) : Message(
    (Constants.MIDI_ANALOG_MESSAGE.toInt() or (pin.position and 0x0F)).toByte(), *content
)

/** two byte digital data format, second nibble of byte 0 gives the port number (e.g. 0x92 is the third port, port 2)
 * 0  digital data, 0x90-0x9F, (MIDI NoteOn, but different data format)
 * 1  digital pins 0-6 bitmask
 * 2  digital pin 7 bitmask
 */
class DigitalMessage(pin: Pin, content: Byte) : Message(
    Constants.MIDI_DIGITAL_MESSAGE, (pin.position and 0x7F).toByte(), content
)


/** toggle analogIn reporting by pin
 * 0  toggle analogIn reporting (0xC0-0xCF) (MIDI Program Change)
 * 1  disable(0)/enable(non-zero)
 */
class AnalogReportMessage(pin: Pin, enable: Boolean) : Message(
    (Constants.MIDI_REPORT_ANALOG.toInt() or (pin.position and 0x0F)).toByte(), if (enable) 1 else 0
)

/** toggle digital port reporting by port (second nibble of byte 0), e.g. 0xD1 is port 1 is pins 8 to 15,
 * 0  toggle digital port reporting (0xD0-0xDF) (MIDI Aftertouch)
 * 1  disable(0)/enable(non-zero)
 */
class DigitalReportMessage(pin: Pin, enable: Boolean) : Message(
    Constants.MIDI_REPORT_DIGITAL, (pin.position and 0x7F).toByte(), if (enable) 1 else 0
)

class PinModeMessage(pin: Pin, mode: Pin.MODE) : Message(
    Constants.MIDI_SET_PIN_MODE, pin.position.toByte(), mode.hex
)