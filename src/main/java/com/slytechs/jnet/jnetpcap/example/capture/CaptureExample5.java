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
package com.slytechs.jnet.jnetpcap.example.capture;

import org.jnetpcap.PcapException;

import com.slytechs.jnet.protocol.core.Ip4;
import com.slytechs.jnet.protocol.core.Tcp;
import com.slytechs.jnet.protocol.packet.Packet;
import com.slytechs.jnet.protocol.packet.meta.PacketFormat;
import com.slytechs.jnet.protocol.web.Html;
import com.slytechs.jnet.protocol.web.Http;
import com.slytechs.jnetpcap.pro.PcapPro;

/**
 * 
 * @author Sly Technologies
 * @author repos@slytechs.com
 */
public class CaptureExample5 {
	/**
	 * Bootstrap the example.
	 *
	 * @param args ignored
	 * @throws PcapException any pcap exceptions
	 */
	public static void main(String[] args) throws PcapException {
		new CaptureExample5().main();
	}

	private final Ip4 ip4 = new Ip4();
	private final Tcp tcp = new Tcp();
	private final Http http = new Http();
	private final Html html = new Html();

	/** Example instance */
	void main() throws PcapException {
		try (PcapPro pcap = PcapPro.create("en0")) {
			pcap
					.setPacketFormatter(new PacketFormat())
					.activate();

			/* Number of packets to capture */
			final int PACKET_COUNT = 10;

			/* Send packets to handler. The generic user parameter can be of any type. */
			pcap.dispatch(PACKET_COUNT, this::nextPacket, "Example 3");

		}
	}

	/** Our packet handler */
	void nextPacket(String user, Packet packet) {

		if (packet.hasHeader(ip4) && ip4.isReassembled())
			System.out.println(ip4.reassembledFragments());

		if (packet.hasHeader(tcp) && tcp.isReassembled())
			System.out.println(tcp.reaseembledSegments());

		if (packet.hasHeader(http) && http.isDechunked())
			System.out.println(http.dechunkedData());

		if (packet.hasHeader(http) && http.isDecompressed())
			System.out.println(http.decompressedData());

		if (packet.hasHeader(html))
			System.out.println(html.text());

		System.out.println(packet);
	}
}
