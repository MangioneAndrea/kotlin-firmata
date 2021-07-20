package exceptions

import board.Pin

class ActionNotSupportedWithCurrentModeException(pin: Pin, mode: Pin.MODE, action: String) :
    Exception("Pin ${pin.position} with mode $mode is not currently capable of $action")