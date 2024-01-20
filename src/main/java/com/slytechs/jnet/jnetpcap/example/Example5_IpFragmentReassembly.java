/*
 * Apache License, Version 2.0
 * 
 * Copyright 2013-2022 Sly Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.slytechs.jnet.jnetpcap.example;

import org.jnetpcap.PcapException;

import com.slytechs.jnet.jnetpcap.NetPcap;

/**
 * The example shows how to use PcapPro's IPv4 and IPv6 fragmentation reassembly
 * feature.
 * 
 *  * <pre>
 * struct pack_record_s {
 * 	uint32_t
 * 		ordinal:4,  // Index within the protocol pack
 *      base:4
 * 		pack:4,     // Protocol pack unique number
 * 		size:11,    // (Optional) Size of the protocol header (in units of 32-bits)
 * 		offset:11;  // (Optional) Offset into the packet (in units of 8-bit bytes)
 * }
 * </pre>
 */
public class Example5_IpFragmentReassembly {
	
	/** Bootstrap the example */
	public static void main(String[] args) throws PcapException {
		new Example5_IpFragmentReassembly().main();
	}

	/** Main example */
	void main() throws PcapException {
		final String IP_FRAGMENTED_FILE = "pcaps/IPv4-ipf.pcapng";


		try (NetPcap pcap = NetPcap.openOffline(IP_FRAGMENTED_FILE)) {

			/*
			 * Enable IP Fragment (IPF) reassembly and activate offline capture
			 */
			pcap.enableIpf(true);
			pcap.activate();

			/*
			 * Display reassembled packets
			 * 
			 * Java method reference equivalent to lamda expression:
			 * 
			 * (Packet packet) -> System.out.println(packet)
			 */
			pcap.dispatch(System.out::println);
		}

	}
}
