package board.interfaces

import board.Pin
import firmata.FirmataListener

interface Sensor : FirmataListener {
    fun getValue(): Pin.Status
}