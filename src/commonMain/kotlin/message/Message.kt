package message

import board.Pin
import kotlin.experimental.and
import kotlin.experimental.or

open class Message(vararg var content: Byte) {
    private val lastIndex: Int
        get() = content.size - 1

    val firstNibble get(): Byte = firstByte and 0xF0.toByte()

    val secondNibble get(): Byte = firstByte and 0x0F.toByte()

    val sysexContent get(): Message = partial(1 until lastIndex - 1)

    val firstByte get(): Byte = content.first()

    operator fun get(index: Int): Byte = content[index]

    operator fun plus(other: Message): Message = Message(*content, *other.content)

    private fun partial(range: IntRange): Message = Message(*content.slice(range).toByteArray())

    infix fun startingAt(from: Int): Message = partial(from..lastIndex)

    infix fun endingAt(to: Int): Message = partial(0..to)

    fun isSysex(): Boolean = Midi.START_SYSEX correspondsTo firstByte


    fun print() = println("Message: ${asHexString()}")


    fun splitAll(byte: Byte): Array<Message> {
        val list = ArrayList<Message>()
        var element = ArrayList<Byte>()
        content.forEach {
            if (it == byte) {
                list.add(Message(*element.toByteArray()))
                element = ArrayList()
            } else {
                element.add(it)
            }
        }
        return list.toTypedArray()
    }

    fun asHexString(): String {
        val hexArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        var res = "";
        for (j in content.indices) {
            val v = content[j].toInt() and 0xFF
            res += "0x${hexArray[v ushr 4]}${hexArray[v and 0x0F]} "
        }

        return res
    }


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
    (Midi.ANALOG_MESSAGE.toInt() or (pin.position and 0x0F)).toByte(), *content
)

/** two byte digital data format, second nibble of byte 0 gives the port number (e.g. 0x92 is the third port, port 2)
 * 0  digital data, 0x90-0x9F, (MIDI NoteOn, but different data format)
 * 1  digital pins 0-6 bitmask
 * 2  digital pin 7 bitmask
 */
class DigitalMessage(pin: Pin, content: Byte) : Message(
    Midi.DIGITAL_MESSAGE.get(), (pin.position and 0x7F).toByte(), content
)


/** toggle analogIn reporting by pin
 * 0  toggle analogIn reporting (0xC0-0xCF) (MIDI Program Change)
 * 1  disable(0)/enable(non-zero)
 */
open class AnalogReportMessage(pin: Pin, enable: Boolean) : Message(
    (Midi.REPORT_ANALOG.toInt() or (pin.position)).toByte(), if (enable) 1 else 0
)

class AnalogReportMessageEnable(pin: Pin) : AnalogReportMessage(pin, true)
class AnalogReportMessageDisable(pin: Pin) : AnalogReportMessage(pin, false)

/** toggle digital port reporting by port (second nibble of byte 0), e.g. 0xD1 is port 1 is pins 8 to 15,
 * 0  toggle digital port reporting (0xD0-0xDF) (MIDI Aftertouch)
 * 1  disable(0)/enable(non-zero)
 */
open class DigitalReportMessage(set: Int, enable: Boolean) : Message(
    Midi.REPORT_DIGITAL.get() or (set.toByte() and 0x0F), if (enable) 1 else 0
)

class DigitalReportMessageEnable(set: Int) : DigitalReportMessage(set, true)
class DigitalReportMessageDisable(set: Int) : DigitalReportMessage(set, false)

/** set pin mode
 * 1  set digital pin mode (0xF4) (MIDI Undefined)
 * 2  pin number (0-127)
 * 3  state (INPUT/OUTPUT/ANALOG/PWM/SERVO, 0/1/2/3/4)
 */
class PinModeMessage(pin: Pin, mode: Pin.MODE) : Message(
    Midi.SET_PIN_MODE.get(), pin.position.toByte(), mode.hex
)

/** request version report
 * 0  request version report (0xF9) (MIDI Undefined)
 */
class VersionRequestMessage : Message(
    Midi.REPORT_VERSION.get()
)

/** firmware name and version
 * -------------------------------------------------
 * 0  START_SYSEX       (0xF0)
 * 1  queryFirmware     (0x79)
 * 2  major version     (0-127)
 * 3  minor version     (0-127)
 * 4  first char of firmware name (LSB)
 * 5  first char of firmware name (MSB)
 * 6  second char of firmware name (LSB)
 * 7  second char of firmware name (MSB)
 * ... for as many bytes as it needs
 * N  END_SYSEX         (0xF7)
 */
class FirmwareNameAndVersionMessage {
    // TODO: Not implemented yet
}

/**
 * The capability query provides a list of all modes supported by each pin.
 * Each mode is described by 2 bytes where the first byte is the pin mode
 * (such as digital input, digital output, PWM) and the second byte is the
 * resolution (or sometimes the type of pin such as RX or TX for a UART pin).
 * A value of 0x7F is used as a separator to mark the end each pin's list of modes.
 * The number of pins supported is inferred by the message length.
 * 0  START_SYSEX              (0xF0)
 * 1  CAPABILITY_QUERY         (0x6B)
 * 2  END_SYSEX                (0xF7)
 */
class CapabilityQueryMessage : Message(
    Midi.START_SYSEX.get(),
    Sysex.CAPABILITY_QUERY.get(),
    Midi.END_SYSEX.get()
)
