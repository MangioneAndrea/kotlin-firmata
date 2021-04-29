package exceptions

import board.Pin
import java.lang.Exception

class PinModeNotAvailableException(pin: Pin, mode: Pin.MODE) :
    Exception("Pin ${pin.position} doesn't support mode $mode. Supported modes: ${pin.modes}")