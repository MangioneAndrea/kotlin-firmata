import Util.arduinoUnoCapabilities
import message.Sysex
import firmata.Firmata
import firmata.Firmata.Companion.Led
import message.Midi
import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {
    private fun byteArrayToString(vararg bytes: Byte): String {
        return bytes.map { it.toString() }.toString()
    }

    private var lastMessage: String = ""
    private val connection = MockupConnection { lastMessage = (byteArrayToString(*it)) };

    @Test
    fun TurnLedOn() {
        val firmata = Firmata(connection)
        connection.feedData(arduinoUnoCapabilities)
        firmata.Led(4).turnOn()
        assertEquals(
            byteArrayToString(Midi.DIGITAL_MESSAGE.get(), 4, 1),
            lastMessage
        )
    }

    @Test
    fun TurnLedOff() {
        val firmata = Firmata(connection)
        connection.feedData(arduinoUnoCapabilities)
        firmata.Led(4).turnOff()
        assertEquals(
            byteArrayToString(Midi.DIGITAL_MESSAGE.get(), 4, 0),
            lastMessage
        )
    }
}