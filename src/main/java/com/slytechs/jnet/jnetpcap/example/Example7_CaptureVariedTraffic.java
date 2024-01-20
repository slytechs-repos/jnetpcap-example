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

import com.slytechs.jnet.jnetpcap.NetPcap;
import com.slytechs.jnet.jnetruntime.util.HexStrings;
import com.slytechs.jnet.protocol.Packet;
import com.slytechs.jnet.protocol.core.constants.PacketDescriptorType;
import com.slytechs.jnet.protocol.meta.PacketFormat;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 *
 */
public class Example7_CaptureVariedTraffic {
	private final String PCAP_FILE = "pcaps/varied-traffic-capture-lan.pcapng";

	public static void main(String[] args) throws PcapException {
		new Example7_CaptureVariedTraffic().main();
	}

	void main() throws PcapException {

		try (var pcap = NetPcap.openOffline(PCAP_FILE)) {

			pcap
					.setDescriptorType(PacketDescriptorType.TYPE2)
					.setPacketFormatter(new PacketFormat())
//					.setBufferSize(4, MemoryUnit.KILOBYTES)
//					.setNonBlock(true)
					.activate();

			int FRAME_NO = 400;
			pcap.loop(FRAME_NO + 1, this::nextPacket, FRAME_NO);

			pcap.loop(1, this::nextPacket, FRAME_NO);
		}

	}

	private void nextPacket(int frameNo, Packet packet) {
//		if (frameNo != packet.descriptor().frameNo)
//			return;

		System.out.println(packet.descriptor());

		System.out.println(HexStrings.toHexDump(packet.descriptor().buffer()));

		System.out.print(packet);
	}

}
