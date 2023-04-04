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
package com.slytechs.jnet.jnetpcap.example.capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapException;

import com.slytechs.jnet.protocol.packet.Packet;
import com.slytechs.jnet.runtime.time.Timestamp;
import com.slytechs.jnet.runtime.time.TimestampUnit;
import com.slytechs.jnetpcap.pro.PcapPro;

/**
 * Pcap packet capture example (offline) using jNetPcap. This example
 * demonstrates how to open a capture file (offline), set a packet filter and
 * capture 20 packets by dispatching them to a packet handler which prints a not
 * so useful per packet message.
 * 
 * <ul>
 * <li>Step 1 - use {@link Pcap#openOffline} method to create a pcap packet
 * capture handle and specify a pcap capture file to open.</li>
 * <li>Step 2 - use {@link Pcap#compile} to create a Berkley Packet2 Filter
 * (BpFilter) compatible with libpcap library. This filter will only allow "tcp"
 * type packets to filter through.</li>
 * <li>Step 3 - apply the packet filter using {@link Pcap#setFilter} method</li>
 * <li>Step 4 - dispatch 20 packets to our callback method
 * {@link CaptureExample1#arrayHandler}, one at a time, along with our user
 * opaque object, in this case a string message.</li>
 * <li>Step 5 - lastly our callback method will be called, one packet at a time
 * or 20 times in our case with the first 20 packets containing a tcp header.
 * The callback method receives all the neccessary information about the
 * captured packet such as a 64-bit timestamp, the original packet length as
 * seen on the network, and a byte array containing the packet data. Also our
 * user object, the message string, is also passed through.</li>
 * </ul>
 * 
 * Our callback method takes the given parameters and prints them out to the
 * console/stdout.
 * 
 * <h4>Timestamps</h4>
 * <p>
 * The encoded 64-bit timestamp contains the timestamp data as encoded by the
 * <em>libpcap</em> library when the packet was originally captured. The
 * encoding is of type <em>unix</em> <full>struct timeval</full> which contains
 * 32-bits (MSB) number of a seconds counter, as per the <em>unix time</em> or
 * <em>Epoch Time</em> (that is since January 1, 1970 at midnight) and in the
 * remaining 32-bits (LSB) the number of micro seconds for more accuracy. A
 * comprehensive set of classes are provided to allow you to work with this and
 * other types of timestamps. You can use the {@link TimestampUnit#PCAP_MICRO}
 * conversion methods to work with this type of timestamp. For nano second
 * precision captures, which are also possible with some capture files, the
 * number of nano seconds is encoded in the 32-bit (LSB) instead of micro
 * seconds. You would use the {@link TimestampUnit#PCAP_NANO} conversion methods
 * to work with nano-second precision captures.
 * </p>
 * 
 * <p>
 * First, a {@link com.slytechs.jnet.runtime.time.Timestamp} class is provided
 * which was designed to work with different types of timestamps. The new
 * {@full Timestamp} class does extend the standard java {@full Date} class, so
 * it is suitable for normal {@full DateFormat} formatting.
 * </p>
 * <p>
 * Second, a new {@link com.slytechs.jnet.runtime.time.TimestampUnit} class is
 * provided to convert encoded timestamps from one encoding type to another.
 * This is similar in the way that standard java {@full TimeUnit} class works,
 * but for various network packet timestamp encodings you will find. For example
 * to convert any timestamp to standard epoch milli second, as compatible with
 * {@full System.currentTimeMillis} results, you would do '{@full long millis =
 * TimestampUnit.PCAP_MICRO.toEpochMilli(timestamp)}'. This would convert from
 * <em>unix struct timeval</em> format to java <em>Epoch</em> milliseconds since
 * {@full January 1st, 1970} encoding. Note that some precision is lost during
 * micro to milli second conversion.
 * </p>
 * 
 * <h4>How to launch</h4>
 * <p>
 * This example was launched with the following VM arguments on a Linux system,
 * as it uses some features (<em>JEP-424 - project Panama</em>) that are still
 * incubating. (Note you may see a warning "Using incubator modules:
 * jdk.incubator.foreign" while the feature continues to incubate per the JEP
 * implementors.)
 * </p>
 * 
 * <pre>
 * VM launch args:
 *        -Djava.library.path=/usr/lib/x86_64-linux-gnu
 *        --enable-native-access=org.jnetpcap
 * </pre>
 * 
 * <p>
 * Note 1: <em>-Djava.library.path</em> argument specifies the directory/folder
 * where the native <em>libpcap.so</em> shared library can be found.
 * </p>
 * <p>
 * Note 2: <em>--enable-native-access</em> argument is only neccessary while the
 * <em>foreign-function</em> feature is incubating. Eventually this feature will
 * be moved to full implementation status.
 * </p>
 * 
 * @author Sly Technologies
 * @author Mark Bednarczyk
 * @author repos@slytechs.com
 */
public class CaptureExample1 {

	/**
	 * Bootstrap the example.
	 *
	 * @param args ignored
	 * @throws PcapException any pcap exceptions
	 */
	public static void main(String[] args) throws PcapException {
		new CaptureExample1().main();
	}

	/** Example instance */
	void main() throws PcapException {
		/* Pcap capture file to read */
		final String PCAP_FILE = "pcaps/HTTP.cap";

		/* Automatically close Pcap resource when done */
		try (PcapPro pcap = PcapPro.openOffline(PCAP_FILE)) {

			/* Number of packets to capture */
			final int PACKET_COUNT = 10;

			/* Send packets to handler. The generic user parameter can be of any type. */
			pcap.loop(PACKET_COUNT, (String user, Packet packet) -> {
				System.out.printf("%s: %03d: caplen=%-,5d ts=%s%n",
						user,
						packet.descriptor().frameNo(),
						packet.captureLength(),
						new Timestamp(packet.timestamp(), packet.timestampUnit()));

			}, "Example1");
		}
	}

}
