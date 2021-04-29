package firmata

class MessageBuffer(val callback: (message: Message)->Unit) {

    private val receivedData = ArrayList<Byte>()
    private var openBuffer = ArrayList<Byte>()
    private var missing = 0;



    fun addBytes(vararg bytes: Byte) {
        receivedData.addAll(bytes.toCollection(ArrayList()))
        process()
    }

    private fun process() {
        val toClear = ArrayList<Byte>()
        receivedData.forEach {
            when (it) {
                Constants.MIDI_ANALOG_MESSAGE.get(),
                Constants.MIDI_DIGITAL_MESSAGE.get(),
                Constants.MIDI_REPORT_ANALOG.get(),
                Constants.MIDI_REPORT_DIGITAL.get(),
                Constants.MIDI_SET_DIGITAL_PIN_VALUE.get(),
                Constants.MIDI_SET_PIN_MODE.get(),
                Constants.MIDI_REPORT_VERSION.get(),
                -> {
                    missing = 3;
                }
                Constants.MIDI_SYSTEM_RESET.get() -> {
                    missing = 1;
                }
                Constants.MIDI_START_SYSEX.get() -> {
                    // Until end sysex
                    missing = -1;
                }
                Constants.MIDI_END_SYSEX.get() -> {
                    if (missing < 0) {
                        missing = 1
                    }
                }
            }
            //println("$it vs ${Constants.MIDI_END_SYSEX.get()} equals ${it == Constants.MIDI_END_SYSEX.get()}")
            toClear.add(it)
            if (missing != 0) {
                insert(it)
                //println("Reading ${Message(it).toHex()} -- Message now:${Message(*openBuffer.toByteArray()).toHex()}")
                missing--;
                //println("missing $missing")
                if (missing == 0) {
                    commitAndClear()
                }
            } else {
                //println("ignoring ${Message(it).toHex()}")
            }
        }
        receivedData.removeAll(toClear)
    }

    private fun insert(byte: Byte) {
        openBuffer.add(byte);
    }

    private fun commitAndClear() {
        callback(Message(*openBuffer.toByteArray()))
        openBuffer.clear()
    }


}