package board

class Pin(private val position: Int) {

    fun setStatus(status: Status) {

    }

    override fun hashCode(): Int {
        return position
    }

    enum class Status(value: Int) {
        HIGH(1), LOW(2)
    }

}