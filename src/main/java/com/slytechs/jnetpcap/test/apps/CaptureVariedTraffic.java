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
package com.slytechs.jnetpcap.test.apps;

import org.jnetpcap.PcapException;

import com.slytechs.jnetpcap.pro.PcapPro;
import com.slytechs.protocol.Packet;
import com.slytechs.protocol.meta.PacketFormat;
import com.slytechs.protocol.pack.core.Ip4;
import com.slytechs.protocol.pack.core.Ip4OptRouterAlert;
import com.slytechs.protocol.runtime.util.Detail;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 *
 */
public class CaptureVariedTraffic {
	private final String PCAP_FILE = "pcaps/varied-traffic-capture-lan.pcapng";
	final Ip4 ip4 = new Ip4();
	final Ip4OptRouterAlert router = new Ip4OptRouterAlert();
	final StringBuilder out = new StringBuilder();

	public static void main(String[] args) throws PcapException {
		new CaptureVariedTraffic().main();
	}

	void main() throws PcapException {

		try (var pcap = PcapPro.openOffline(PCAP_FILE)) {

//			pcap.setPacketFormatter(new PacketFormat());

			int FRAME_NO = 400;
//			pcap.loop(FRAME_NO + 1, this::nextPacket, FRAME_NO);
			pcap.loop(0, System.out::println);
		}

	}

	private void nextPacket(int frameNo, Packet packet) {
		PacketFormat format = packet.getFormatter();
		out.setLength(0);

//		if (frameNo != packet.descriptor().frameNo)
//			return;

//		System.out.printf("----------- %4d ------------%n", packet.descriptor().frameNo);
//		System.out.println(packet.descriptor());
//		System.out.printf("----------- %4d ------------%n", packet.descriptor().frameNo);

//		System.out.println(HexStrings.toHexDump(packet.descriptor().buffer()));

		format.formatPacket(packet, out, Detail.HIGH);

		if (packet.hasHeader(ip4) && ip4.hasExtension(router)) {
			out.setLength(0);
			out.append("#%d: ".formatted(packet.descriptor().frameNo()));
			format.formatHeader(router, out, Detail.HIGH);
			System.out.print(out);
		}
	}

}
