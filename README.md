# Examples of jNetPcap API usage
The examples in this modules are a mixture of [**jnetpcap**](https://github.com/slytechs-repos/jnetpcap) and [**jnetpcap-pro**](https://github.com/slytechs-repos/jnetpcap-pro) API usage. Both modules are free, no cost to use in your projects.

## Overview
An important aspect of the API to understand the examples is that the main operative class in **jnetpcap** modules
is the `Pcap` class. It is the starting point for nearly all actions when working with the module. Additionally, the class `PcapPro` is a subclass of `Pcap` class (ie.  `class PcapPro extends Pcap`) and thus provides additional capabilities in on top of the base `Pcap` API.

### Dependencies
This example module relies on several optional modules to be included on the `module path`. The base module **jnetpcap** has no other module dependencies, but the module **jnetpcap-pro** has a dependency on **core-protocols** module. Further more the examples here also include **web-protocols** module to demonstrate usage of `Http` header class and its features.

> **Note** **jNetPcap API** applies to both **jnetpcap (Apache License)** and **jnetpcap-pro** modules. However the examples will specify when any particular feature is only available in **jnetpcap-pro API**.

## Offline packet capture examples ([Source Code](https://github.com/slytechs-repos/jnetpcap-examples/tree/main/src/main/java/com/slytechs/jnet/jnetpcap/example/capture))
The examples in this section relate to various ways of capturing packets using **jNetPcap** APIs from offline files or pcap files (both standard and NG file formats).

There are some commonalities to each example. Here are the basic steps:

1. Optionally, check the `Pcap.checkPcapVersion(Pcap.VERSION))` - ensures that the installed pcap runtime is compatible with the version of pcap the application was compiled with
2. Open an offline pcap capture in a `try-with-resource` statement to ensure that proper `Pcap.close` is issued when done using the pcap handle.
    - ie. `try (Pcap pcap = Pcap.openOffline(FILENAME)) {...}`
    - ie. `try (PcapPro pcap = PcapPro.openOffline(FILENAME)) {...}`
3. Optionally, set some parameters on the pcap handle
4. Start packet capture from offline file using either a `Pcap.dispatch(...)` or `Pcap.loop(...)` calls
5. Lastly, provide a packet handler either as a receiving method for the appropriate `PcapHandler` interface or a simple lambda expression

To view all of the **capture** examples [click here](https://github.com/slytechs-repos/jnetpcap-examples/tree/main/src/main/java/com/slytechs/jnet/jnetpcap/example/capture).

## NOTE!!!! Document still under construction
