package connection

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import com.felhr.usbserial.UsbSerialDevice
import firmata.Message
import android.hardware.usb.UsbManager
import com.felhr.usbserial.UsbSerialInterface


class UsbSerialConnection(private val device: UsbDevice, private val usbManager: UsbManager) : Connection {
    private var usbSerialDevice: UsbSerialDevice? = null;
    private var connection: UsbDeviceConnection? = null;


    override fun connect(): Boolean {
        connection = usbManager.openDevice(device);
        usbSerialDevice = UsbSerialDevice.createUsbSerialDevice(device, connection);
        if (usbSerialDevice != null && usbSerialDevice!!.open()) {
            usbSerialDevice!!.setBaudRate(9600);
            usbSerialDevice!!.setDataBits(UsbSerialInterface.DATA_BITS_8)
            usbSerialDevice!!.setStopBits(UsbSerialInterface.STOP_BITS_1)
            usbSerialDevice!!.setParity(UsbSerialInterface.PARITY_NONE)
            usbSerialDevice!!.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)
            return true
        }
        return false;
    }

    override fun read(): Message {
        val byteArray = ByteArray(32)
        usbSerialDevice?.syncRead(byteArray, 2000)
        return Message(byteArray)

    }

    override fun asyncRead(callback: (Message) -> Unit) {
        usbSerialDevice?.read {
            callback(Message(it))
        }
    }

    override fun write(message: Message) {
        usbSerialDevice?.write(message.content);
    }

    override fun close() {
        usbSerialDevice?.close();
    }
}