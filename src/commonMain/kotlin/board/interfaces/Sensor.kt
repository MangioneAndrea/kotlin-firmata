package board.interfaces

import board.Pin

interface Sensor {
    fun getValue(): Pin.Status
}