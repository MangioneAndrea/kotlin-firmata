package board.interfaces

import board.Pin

interface Actor {
    fun setValue(vararg status: Pin.Status)
}

