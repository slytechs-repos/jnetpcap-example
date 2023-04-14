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
import com.slytechs.protocol.Packet;
import com.slytechs.protocol.meta.PacketFormat;
import com.slytechs.protocol.runtime.util.HexStrings;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 *
 */
public class CaptureVariedTraffic {
	private final String PCAP_FILE = "/opt/pcaps/markb-captures/varied-traffic-capture-lan.pcapng";

	public static void main(String[] args) throws PcapException {
		new CaptureVariedTraffic().main();
	}

	void main() throws PcapException {

		try (var pcap = PcapPro.openOffline(PCAP_FILE)) {

			pcap.setPacketFormatter(new PacketFormat());

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
