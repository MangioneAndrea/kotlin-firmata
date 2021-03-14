package board.interfaces

abstract class AbstractPin(open var position: Int) {

    override fun hashCode(): Int {
        return position
    }
}