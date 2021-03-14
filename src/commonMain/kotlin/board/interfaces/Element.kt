package board.interfaces

import board.Pin

interface Element {
    val pins: HashSet<Pin>
    val MIN_PINS: Int
    val MAX_PINS: Int

    companion object {
        fun assertPins(caller: Element) {
            if (caller.pins.size < caller.MIN_PINS || caller.pins.size > caller.MIN_PINS) {
                throw Error(
                    "Wrong amount of pins on ${caller::class.simpleName}, required between ${caller.MIN_PINS} " +
                            "and ${caller.MAX_PINS}. Given: ${caller.pins.size}"
                )
            }
        }

    }
}