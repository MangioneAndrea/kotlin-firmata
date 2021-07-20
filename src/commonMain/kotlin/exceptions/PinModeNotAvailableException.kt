package exceptions

import board.Pin

class PinModeNotAvailableException(pin: Pin, mode: Pin.MODE) :
    Exception("Pin ${pin.position} doesn't support mode $mode. Supported modes: ${pin.modes}")