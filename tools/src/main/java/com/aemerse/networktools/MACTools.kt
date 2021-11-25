package com.aemerse.networktools

import java.util.regex.Pattern

object MACTools {
    private val PATTERN_MAC: Pattern = Pattern.compile(
        "^([0-9A-Fa-f]{2}[\\.:-]){5}([0-9A-Fa-f]{2})$"
    )

    /**
     * Validates a provided MAC address
     *
     * @param macAddress - the MAC address to check
     * @return - true if it is valid MAC address in IEEE802 format (either hyphen or colon separated)
     * eg: "01:23:45:67:89:AB" or "01-23-45-67-89-AB"
     */
    fun isValidMACAddress(macAddress: String?): Boolean {
        return macAddress != null && PATTERN_MAC.matcher(macAddress).matches()
    }

    /**
     * Convert a MAC string to bytes
     *
     * @param macStr - MAC string in IEEE802 format (either hyphen or colon separated)
     * eg: "01:23:45:67:89:AB" or "01-23-45-67-89-AB"
     * @return - MAC formatted in bytes
     * @throws IllegalArgumentException - if mac address is invalid
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun getMacBytes(macStr: String?): ByteArray {
        if (macStr == null) throw IllegalArgumentException("Mac Address cannot be null")
        val bytes = ByteArray(6)
        val hex = macStr.split("(\\:|\\-)")
        if (hex.size != 6) {
            throw IllegalArgumentException("Invalid MAC address.")
        }
        try {
            for (i in 0..5) {
                bytes[i] = Integer.parseInt(hex[i], 16) as Byte
            }
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid hex digit in MAC address.")
        }
        return bytes
    }
}