/*
 * Sly Technologies Free License
 * 
 * Copyright 2023 Sly Technologies Inc.
 *
 * Licensed under the Sly Technologies Free License (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.slytechs.com/free-license-text
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.slytechs.jnetpcap.examples;

import org.jnetpcap.PcapException;

import com.slytechs.jnetpcap.pro.PcapPro;
import com.slytechs.protocol.runtime.util.Detail;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 */
public class Example6_smallest_footprint {
	final String LAN_FILE = "pcaps/LAN-1.pcapng";

	/** Bootstrap the example */
	public static void main(String[] args) throws PcapException {
		new Example6_smallest_footprint().main();
	}

	/**
	 * Shortest example possible.
	 *
	 * @throws PcapException the pcap exception
	 */
	void main() throws PcapException {

		try (var pcap = PcapPro.openOffline("pcaps/IPv4-ipf.pcapng")) {
//		try (var pcap = PcapPro.openOffline(LAN_FILE)) {
//			pcap.enableIpf(true).activate();
//			pcap.setFilter(pcap.compile("arp", true));
			pcap.dispatch(packet -> System.out.println(packet.toString(Detail.HIGH)));
		}

	}
}
