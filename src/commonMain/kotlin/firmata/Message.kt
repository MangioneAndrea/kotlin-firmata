package firmata

import board.Pin

class Message {
    private var type: Byte;
    private var pin: Pin? = null;
    private var content: Byte? = null;
    private var arrayContent: ByteArray? = null;

    /** two byte digital data format, second nibble of byte 0 gives the port number (e.g. 0x92 is the third port, port 2)
     * 0  digital data, 0x90-0x9F, (MIDI NoteOn, but different data format)
     * 1  digital pins 0-6 bitmask
     * 2  digital pin 7 bitmask
     */
    constructor(type: Byte, pin: Pin?, content: Byte) {
        this.type = type;
        this.pin = pin;
        this.content = content;
    }

    /** analog 14-bit data format
     * 0  analog pin, 0xE0-0xEF, (MIDI Pitch Wheel)
     * 1  analog least significant 7 bits
     * 2  analog most significant 7 bits
     */
    constructor(type: Byte, pin: Pin?, content: ByteArray) {
        this.type = type;
        this.pin = pin;
        this.arrayContent = content;
    }

    constructor(bytes: ByteArray) : this(bytes[0], null, bytes[2])


    fun asValidFirmataMessage(): ByteArray {
        if (content !== null) {
            return byteArrayOf(
                type,
                (pin!!.position and 0x7F).toByte(),
                content!!
            )
        }
        if (arrayContent !== null) {
            return byteArrayOf(
                (type.toInt() or (pin!!.position and 0x0F)).toByte(),
                *arrayContent!!
            )
        }
        throw Exception("Invalid message with no content")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        other as Message
        if (content != other) return false
        return true
    }

    override fun hashCode(): Int {
        return content.hashCode()
    }
}