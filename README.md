# Networking Tools 

Collection of handy networking tools for everyday development.

* Port Scanning
* Subnet Device Finder (discovers devices on local network)
* Ping
* Wake-On-Lan
* & More :)

### Port Scanning

A simple TCP / UDP port scanner, fast and easy to use. By default it will try and guess the best timeout and threads to use while scanning depending on if the address looks like localhost, local network or remote. You can override these yourself by calling setNoThreads() and setTimeoutMillis()

### Subnet Devices

Finds devices that respond to ping that are on the same subnet as the current device. You can set the timeout for the ping with setTimeOutMillis() \[default 2500\] and the number of threads with setNoThreads() \[default 255\]

### Ping

Uses the native ping binary if available on the device (some devices come without it) and falls back to a TCP request on port 7 (echo request) if not.

### Wake-On-Lan

Sends a Wake-on-Lan packet to the IP / MAC address

# Contributing

You are welcome to contribute to this project!
