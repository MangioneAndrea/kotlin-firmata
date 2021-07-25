package message

enum class Serial(private val byte: Byte) {
    SERIAL_CONFIG(0x10),
    SERIAL_WRITE(0x20),
    SERIAL_READ(0x30),
    SERIAL_REPLY(0x40),
    SERIAL_CLOSE(0x50),
    SERIAL_FLUSH(0x60),
    SERIAL_LISTEN(0x70);


    infix fun correspondsTo(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        return get() == other
    }

    fun get(): Byte = byte;
    enum class Port(private val address: Byte) {
        HW_SERIAL0(0x00),
        HW_SERIAL1(0x01),
        HW_SERIAL2(0x02),
        HW_SERIAL3(0x03),
        SW_SERIAL0(0x08),
        SW_SERIAL1(0x09),
        SW_SERIAL2(0x0A),
        SW_SERIAL3(0x0B);

        infix fun correspondsTo(other: Any?): Boolean {
            if (other == null) return false
            if (this === other) return true
            return get() == other
        }

        fun get(): Byte = address;
    }

    enum class ReadMode(private val byte: Byte) {
        SERIAL_READ_MODE_CONTINUOUS(0x00),
        SERIAL_READ_MODE_STOP(0x01);

        fun get(): Byte = byte;
    }
}