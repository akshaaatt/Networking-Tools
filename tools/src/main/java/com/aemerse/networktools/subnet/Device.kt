package com.aemerse.networktools.subnet

import java.net.InetAddress

class Device(ip: InetAddress) {
    @JvmField
    var ip: String = ip.getHostAddress()

    @JvmField
    var hostname: String = ip.getCanonicalHostName()

    @JvmField
    var mac: String? = null
    @JvmField
    var time = 0f

    override fun toString(): String {
        return "Device{" +
                "ip='" + ip + '\'' +
                ", hostname='" + hostname + '\'' +
                ", mac='" + mac + '\'' +
                ", time=" + time +
                '}'
    }

}