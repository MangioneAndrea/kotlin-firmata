package board.interfaces

abstract class AbstractPin(open var position: Int) {

    override fun hashCode(): Int {
        return position
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AbstractPin

        if (position == other.position) return true

        return false
    }
}