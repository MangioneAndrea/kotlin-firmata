package message

class MessageBuffer(val callback: (message: Message) -> Unit) {

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
                Midi.ANALOG_MESSAGE.get(),
                Midi.DIGITAL_MESSAGE.get(),
                Midi.REPORT_ANALOG.get(),
                Midi.REPORT_DIGITAL.get(),
                Midi.SET_DIGITAL_PIN_VALUE.get(),
                Midi.SET_PIN_MODE.get(),
                Midi.REPORT_VERSION.get(),
                -> {
                    missing = 3;
                }
                Midi.SYSTEM_RESET.get() -> {
                    missing = 1;
                }
                Midi.START_SYSEX.get() -> {
                    // Until end sysex
                    missing = -1;
                }
                Midi.END_SYSEX.get() -> {
                    if (missing < 0) {
                        missing = 1
                    }
                }
            }
            toClear.add(it)
            if (missing != 0) {
                insert(it)
                missing--;
                if (missing == 0) {
                    commitAndClear()
                }
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