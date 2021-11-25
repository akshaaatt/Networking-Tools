package com.aemerse.networktools.ping

class PingOptions {
    @JvmField
    var timeoutMillis = 1000
    @JvmField
    var timeToLive = 128
    fun getTimeoutMillis(): Int {
        return timeoutMillis
    }

    fun setTimeoutMillis(timeoutMillis: Int) {
        this.timeoutMillis = Math.max(timeoutMillis, 1000)
    }

    fun getTimeToLive(): Int {
        return timeToLive
    }

    fun setTimeToLive(timeToLive: Int) {
        this.timeToLive = Math.max(timeToLive, 1)
    }
}