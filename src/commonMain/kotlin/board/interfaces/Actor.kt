package board.interfaces

import board.Pin

interface Actor {
    fun setValue(status: Pin.Status)
}

