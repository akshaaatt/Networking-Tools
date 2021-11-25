package com.aemerse.networktools.ping

import java.net.InetAddress

class PingStats(
    ia: InetAddress,
    noPings: Long,
    packetsLost: Long,
    totalTimeTaken: Float,
    minTimeTaken: Float,
    maxTimeTaken: Float
) {
    private val ia: InetAddress
    val noPings: Long
    val packetsLost: Long
    val averageTimeTaken: Float
    val minTimeTaken: Float
    val maxTimeTaken: Float
    val isReachable: Boolean
    val address: InetAddress
        get() = ia
    val averageTimeTakenMillis: Long
        get() = (averageTimeTaken * 1000).toLong()
    val minTimeTakenMillis: Long
        get() = (minTimeTaken * 1000).toLong()
    val maxTimeTakenMillis: Long
        get() = (maxTimeTaken * 1000).toLong()

    @Override
    override fun toString(): String {
        return "PingStats{" +
                "ia=" + ia +
                ", noPings=" + noPings +
                ", packetsLost=" + packetsLost +
                ", averageTimeTaken=" + averageTimeTaken +
                ", minTimeTaken=" + minTimeTaken +
                ", maxTimeTaken=" + maxTimeTaken +
                '}'
    }

    init {
        this.ia = ia
        this.noPings = noPings
        this.packetsLost = packetsLost
        averageTimeTaken = totalTimeTaken / noPings
        this.minTimeTaken = minTimeTaken
        this.maxTimeTaken = maxTimeTaken
        isReachable = noPings - packetsLost > 0
    }
}