# Examples of jNetPcap API usage
The examples in this modules are a mixture of [**jnetpcap**](https://github.com/slytechs-repos/jnetpcap) and [**jnetpcap-pro**](https://github.com/slytechs-repos/jnetpcap-pro) API usage. Both modules are free to use in your projects.

An important aspect of the API to understand the examples is that the main operative class in **jnetpcap** modules
is the `Pcap` class. It is the starting point for nearly all actions when working with the module. Additionally, the class `PcapPro` is a subclass of `Pcap` class (ie.  `class PcapPro extends Pcap`) and thus provides additional capabilities in on top of the base `Pcap` API.

## Dependencies
This example module relies on several optional modules to be included on the `module path`. The base module **jnetpcap** has no other module dependencies, but the module **jnetpcap-pro** has a dependency on **

> **Note** **jNetPcap API** applies to both **jnetpcap (Apache License)** and **jnetpcap-pro** modules. However the examples will specify when any particular feature is only available in **jnetpcap-pro API**.

## How to capture packets
The examples in this section relate to various ways of capturing packets using **jNetPcap** APIs. 
