import Util.arduinoUnoCapabilities
import exceptions.PinModeNotAvailableException
import exceptions.PinNotOnBoardException
import firmata.Firmata
import firmata.Firmata.Companion.Led
import message.CapabilityQueryMessage
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
        assertEquals(19, firmata.selectedBoard!!.pins.size)
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

}