package board.boards

import board.Pin

class Board(size: Int) {
    val pins: Array<Pin?> = Array(size) { null }

    fun setPin(index: Int, pin: Pin) {
        pins[index] = pin;
    }

    operator fun get(index: Int): Pin? {
        return pins[index]
    }

    val capabilities: Map<Int, HashSet<Pin.MODE>>
        get() = pins.associate { pin ->
            Pair(pin!!.position, pin.modes)
        }
}
