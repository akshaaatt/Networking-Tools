package com.aemerse.networktools.ping

import java.net.InetAddress

class PingResult(ia: InetAddress?) {
    val ia: InetAddress?
    var isReachable = false
    var error: String? = null
    @JvmField
    var timeTaken = 0f
    var fullString: String? = null
    var result: String? = null
    fun hasError(): Boolean {
        return error != null
    }

    val address: InetAddress?
        get() = ia

    @Override
    override fun toString(): String {
        return "PingResult{" +
                "ia=" + ia +
                ", isReachable=" + isReachable +
                ", error='" + error + '\'' +
                ", timeTaken=" + timeTaken +
                ", fullString='" + fullString + '\'' +
                ", result='" + result + '\'' +
                '}'
    }

    init {
        this.ia = ia
    }
}