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
package com.slytechs.jnetpcap.examples;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapException;

import com.slytechs.jnetpcap.pro.PcapPro;
import com.slytechs.protocol.Packet;
import com.slytechs.protocol.meta.PacketFormat;
import com.slytechs.protocol.pack.core.Ethernet;
import com.slytechs.protocol.pack.core.Ip4;
import com.slytechs.protocol.pack.core.Ip4Option.Ip4RouterOption;
import com.slytechs.protocol.pack.core.Tcp;
import com.slytechs.protocol.pack.core.constants.PacketDescriptorType;

/**
 * Example showing how to capture offline packets and dispatch to a user packet
 * handler of type {@code PcapProHandler.OfPacket}. The example also enables IP
 * fragment reassembly which will cause fully reassembled IP datagrams to be
 * delivered to the user handler as packets and also drop the original
 * reassembled fragments. We are only interested in non-fragment IP datagrams.
 */
public class Example1_CapturePacketsAndPrintHeaders {

	/**
	 * Bootstrap the example.
	 *
	 * @param args ignored
	 * @throws PcapException any pcap exceptions
	 */
	public static void main(String[] args) throws PcapException {
		new Example1_CapturePacketsAndPrintHeaders().main();
	}

	/** Example instance */
	void main() throws PcapException {
		/* Pcap capture file to read */
		final String PCAP_FILE = "pcaps/HTTP.cap";

		/* Make sure we have a compatible Pcap runtime installed */
		Pcap.checkPcapVersion(Pcap.VERSION);

		/*
		 * Automatically close Pcap resource when done and checks the client and
		 * installed runtime API versions to ensure they are compatible.
		 */
		try (PcapPro pcap = PcapPro.openOffline(PCAP_FILE)) { // Pro API

			/* Set a pretty print formatter to toString() method */
			pcap.setPacketFormatter(new PacketFormat())
				.setDescriptorType(PacketDescriptorType.TYPE1);

			/* Number of packets to capture */
			final int PACKET_COUNT = 10;

			/* Pro API! Create protocol headers and reuse inside the dispatch handler */
			final Ethernet ethernet = new Ethernet();
			final Ip4 ip4 = new Ip4();
			final Tcp tcp = new Tcp();
			final Ip4RouterOption router = new Ip4RouterOption();

			/* Capture packets and access protocol headers */
			pcap.dispatch(PACKET_COUNT, (String user, Packet packet) -> { // Pro API
				
				System.out.println(packet.descriptor());

				// If present, printout ethernet header
				if (packet.hasHeader(ethernet))
					System.out.println(ethernet);
				
				// If present, printout ip4 header
				if (packet.hasHeader(ip4))
					System.out.println(ip4);

				// If present, printout IPv4.router header extension
				if (packet.hasHeader(ip4) && ip4.hasExtension(router))
					System.out.println(router);

				// If present, printout tcp header
				if (packet.hasHeader(tcp))
					System.out.println(tcp);

			}, "Example2 - Hello World");
		}
	}
}
