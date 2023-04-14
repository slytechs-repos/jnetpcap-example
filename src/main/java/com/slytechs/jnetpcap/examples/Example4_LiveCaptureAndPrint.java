/*
 * MIT License
 * 
 * Copyright (c) 2020 Sly Technologies Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.slytechs.jnetpcap.examples;

import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapException;
import org.jnetpcap.PcapIf;

import com.slytechs.jnetpcap.pro.PcapPro;
import com.slytechs.protocol.Packet;
import com.slytechs.protocol.meta.PacketFormat;
import com.slytechs.protocol.pack.core.Ip4;
import com.slytechs.protocol.pack.core.Tcp;
import com.slytechs.protocol.pack.core.constants.PacketDescriptorType;
import com.slytechs.protocol.pack.web.Html;
import com.slytechs.protocol.pack.web.Http;
import com.slytechs.protocol.runtime.util.MemoryUnit;

/**
 * Pcap packet capture and reassembly example using jNetPcap.
 */
public class Example4_LiveCaptureAndPrint {

	private static final Ip4 ip4 = new Ip4();
	private static final Tcp tcp = new Tcp();
	private static final Http http = new Http();
	private static final Html html = new Html();

	/** Example instance */
	public static void main(String[] args) throws PcapException {

		List<PcapIf> deviceList = Pcap.findAllDevs();
		PcapIf device = deviceList.get(0);

		System.out.println("Opening device '%s'".formatted(device.name()));

		try (PcapPro pcap = PcapPro.create(device)) { // Pro API

			/* Pro API! Set packet descriptor type and pretty print formatter */
			pcap
					.setDescriptorType(PacketDescriptorType.TYPE2)
					.setPacketFormatter(new PacketFormat())
					.setBufferSize(MemoryUnit.KILOBYTES.toIntBytes(4))
					.setNonBlock(true)
					.activate();

			/* Number of packets to capture */
			final int PACKET_COUNT = 1;

			pcap.loop(PACKET_COUNT, Example4_LiveCaptureAndPrint::nextPacket, "Example4"); // Pro API
		}
	}

	/* Out packet handler */
	private static void nextPacket(String user, Packet packet) {

		if (packet.hasHeader(ip4))
			System.out.println(ip4);

		if (packet.hasHeader(tcp))
			System.out.println(tcp);

		if (packet.hasHeader(http))
			System.out.println(http);

		if (packet.hasHeader(http))
			System.out.println(http);

		if (packet.hasHeader(html))
			System.out.println(html);

		System.out.println(packet);
	}
}
