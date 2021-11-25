package com.aemerse.networktools.ping

import com.aemerse.networktools.IPTools
import java.io.BufferedReader
import java.io.IOException

import java.io.InputStreamReader

import java.net.InetAddress


object PingNative {
    @Throws(IOException::class, InterruptedException::class)
    fun ping(host: InetAddress?, pingOptions: PingOptions): PingResult {
        val pingResult = PingResult(host)
        if (host == null) {
            pingResult.isReachable = false
            return pingResult
        }
        val echo: StringBuilder = StringBuilder()
        val runtime: Runtime = Runtime.getRuntime()
        val timeoutSeconds: Int = Math.max(pingOptions.timeoutMillis / 1000, 1)
        val ttl: Int = Math.max(pingOptions.timeToLive, 1)
        var address: String = host.getHostAddress()
        var pingCommand = "ping"
        if (address != null) {
            if (IPTools.isIPv6Address(address)) {
                // If we detect this is a ipv6 address, change the to the ping6 binary
                pingCommand = "ping6"
            } else if (!IPTools.isIPv4Address(address)) {
                // Address doesn't look to be ipv4 or ipv6, but we could be mistaken
            }
        } else {
            // Not sure if getHostAddress ever returns null, but if it does, use the hostname as a fallback
            address = host.getHostName()
        }
        val proc: Process = runtime.exec("$pingCommand -c 1 -W $timeoutSeconds -t $ttl $address")
        proc.waitFor()
        val exit: Int = proc.exitValue()
        val pingError: String
        when (exit) {
            0 -> {
                val reader = InputStreamReader(proc.getInputStream())
                val buffer = BufferedReader(reader)
                var line: String?
                while (buffer.readLine().also { line = it } != null) {
                    echo.append(line).append("\n")
                }
                return getPingStats(pingResult, echo.toString())
            }
            1 -> pingError = "failed, exit = 1"
            else -> pingError = "error, exit = 2"
        }
        pingResult.error = pingError
        proc.destroy()
        return pingResult
    }

    /**
     * getPingStats interprets the text result of a Linux activity_ping command
     *
     * Set pingError on error and return null
     *
     * http://en.wikipedia.org/wiki/Ping
     *
     * PING 127.0.0.1 (127.0.0.1) 56(84) bytes of data.
     * 64 bytes from 127.0.0.1: icmp_seq=1 ttl=64 time=0.251 ms
     * 64 bytes from 127.0.0.1: icmp_seq=2 ttl=64 time=0.294 ms
     * 64 bytes from 127.0.0.1: icmp_seq=3 ttl=64 time=0.295 ms
     * 64 bytes from 127.0.0.1: icmp_seq=4 ttl=64 time=0.300 ms
     *
     * --- 127.0.0.1 activity_ping statistics ---
     * 4 packets transmitted, 4 received, 0% packet loss, time 0ms
     * rtt min/avg/max/mdev = 0.251/0.285/0.300/0.019 ms
     *
     * PING 192.168.0.2 (192.168.0.2) 56(84) bytes of data.
     *
     * --- 192.168.0.2 activity_ping statistics ---
     * 1 packets transmitted, 0 received, 100% packet loss, time 0ms
     *
     * # activity_ping 321321.
     * activity_ping: unknown host 321321.
     *
     * 1. Check if output contains 0% packet loss : Branch to success - Get stats
     * 2. Check if output contains 100% packet loss : Branch to fail - No stats
     * 3. Check if output contains 25% packet loss : Branch to partial success - Get stats
     * 4. Check if output contains "unknown host"
     *
     * @param pingResult - the current ping result
     * @param s - result from ping command
     *
     * @return The ping result
     */
    fun getPingStats(pingResult: PingResult, s: String): PingResult {
        var s = s
        val pingError: String
        if (s.contains("0% packet loss")) {
            val start = s.indexOf("/mdev = ")
            val end = s.indexOf(" ms\n", start)
            pingResult.fullString = s
            if (start == -1 || end == -1) {
                pingError = "Error: $s"
            } else {
                s = s.substring(start + 8, end)
                val stats = s.split("/")
                pingResult.isReachable = true
                pingResult.result = s
                pingResult.timeTaken = stats[1] as Float
                return pingResult
            }
        } else if (s.contains("100% packet loss")) {
            pingError = "100% packet loss"
        } else if (s.contains("% packet loss")) {
            pingError = "partial packet loss"
        } else if (s.contains("unknown host")) {
            pingError = "unknown host"
        } else {
            pingError = "unknown error in getPingStats"
        }
        pingResult.error = pingError
        return pingResult
    }
}