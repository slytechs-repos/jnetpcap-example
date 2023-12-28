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
package com.slytechs.jnet.jnetpcap.example;

import org.jnetpcap.PcapException;

import com.slytechs.jnet.jnetpcap.PcapPro;
import com.slytechs.jnet.jnetruntime.util.Detail;
import com.slytechs.jnet.protocol.core.Icmp;
import com.slytechs.jnet.protocol.core.Icmp4;
import com.slytechs.jnet.protocol.core.Icmp4Echo;
import com.slytechs.jnet.protocol.core.Ip;
import com.slytechs.jnet.protocol.core.Ip4;

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
//			pcap.dispatch(packet -> System.out.println(packet.toString(Detail.HIGH)));

			Ip ip = new Ip();
			Ip4 ip4 = new Ip4();
			Icmp icmp = new Icmp();
			Icmp4 icmp4 = new Icmp4();
			Icmp4Echo icmp4Echo = new Icmp4Echo();

			pcap.dispatch(packet -> {

				System.out.println(packet.descriptor().toString(Detail.HIGH));

				if (packet.hasHeader(ip))
					System.out.println(ip.toString(Detail.HIGH));

				if (packet.hasHeader(ip4))
					System.out.println(ip4);

				if (packet.hasHeader(icmp))
					System.out.println(icmp);

				if (packet.hasHeader(icmp4))
					System.out.println(icmp4);

				if (packet.hasHeader(icmp4Echo))
					System.out.println(icmp4Echo);
				
				System.out.println(packet);
			});
		}

	}
}
