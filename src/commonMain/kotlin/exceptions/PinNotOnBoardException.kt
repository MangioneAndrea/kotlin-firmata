package exceptions

import java.lang.Exception

class PinNotOnBoardException(pin: Int) : Exception("The board doesn't have a pin at position $pin");