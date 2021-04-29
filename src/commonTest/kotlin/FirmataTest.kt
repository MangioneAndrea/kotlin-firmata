import exceptions.PinModeNotAvailableException
import exceptions.PinNotOnBoardException
import firmata.CapabilityQueryMessage
import firmata.Firmata
import firmata.Firmata.Companion.Led
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class FirmataTest {
    private fun byteArrayToString(vararg bytes: Byte): String {
        return bytes.map { it.toString() }.toString()
    }

    private var lastMessage: ByteArray = byteArrayOf()
    private val connection = MockupConnection { lastMessage = it };


    @Test
    fun BoardCapabilityCanBeSent() {
        val msg = CapabilityQueryMessage();
        Firmata(connection).sendRequest(msg);
        assertEquals(
            msg.content,
            lastMessage
        )
    }

    @Test
    fun BoardCapabilityCanBeSet() {
        val firmata = Firmata(connection);
        connection.feedData(arduinoUnoCapabilities)
        assertNotNull(firmata.selectedBoard)
        assertEquals(19, firmata.selectedBoard.pins.size)
    }

    @Test
    fun BoardCapabilityRestrictPinUsage() {
        val firmata = Firmata(connection);
        connection.feedData(arduinoUnoCapabilities)
        assertFailsWith(PinNotOnBoardException::class) { firmata.Led(20) }
    }

    @Test
    fun BoardCapabilityRestrictPinMode() {
        val firmata = Firmata(connection);
        connection.feedData(arduinoUnoCapabilities)
        assertEquals(3, firmata.Led(3).pins[0].position)
        assertFailsWith(PinModeNotAvailableException::class) { firmata.Led(0) }
    }

    private val arduinoUnoCapabilities = byteArrayOf(
        0xF0.toByte(),
        0x6C,
        0x7F,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x03,
        0x08,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x03,
        0x08,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x03,
        0x08,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x03,
        0x08,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x03,
        0x08,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x03,
        0x08,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x02,
        0x0A,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x02,
        0x0A,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x02,
        0x0A,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x02,
        0x0A,
        0x04,
        0x0E,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x02,
        0x0A,
        0x04,
        0x0E,
        0x06,
        0x01,
        0x7F,
        0x00,
        0x01,
        0x0B,
        0x01,
        0x01,
        0x01,
        0x02,
        0x0A,
        0x04,
        0x0E,
        0x06,
        0x01,
        0x7F,
        0xF7.toByte()
    )
}