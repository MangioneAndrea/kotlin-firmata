package board

import board.interfaces.Actor
import board.interfaces.Element

class Motor : Element, Actor {
    override val pins = HashSet<Pin>();
    override val MIN_PINS = 2;
    override val MAX_PINS = 2;

    override fun setValue(status: Pin.Status) {
        TODO("Not yet implemented")
    }
}