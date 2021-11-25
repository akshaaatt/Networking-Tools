package com.aemerse.networktools.portscanning

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException

object PortScanUDP {
    /**
     * Check if a port is open with UDP, note that this isn't reliable
     * as UDP will does not send ACKs
     *
     * @param ia            - address to scan
     * @param portNo        - port to scan
     * @param timeoutMillis - timeout
     * @return - true if port is open, false if not or unknown
     */
    @JvmStatic
    fun scanAddress(ia: InetAddress?, portNo: Int, timeoutMillis: Int): Boolean {
        try {
            val bytes = ByteArray(128)
            val dp = DatagramPacket(bytes, bytes.size)
            val ds = DatagramSocket()
            ds.setSoTimeout(timeoutMillis)
            ds.connect(ia, portNo)
            ds.send(dp)
            ds.isConnected()
            ds.receive(dp)
            ds.close()
        } catch (e: SocketTimeoutException) {
            return true
        } catch (ignore: Exception) {
        }
        return false
    }
}