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

import org.jnetpcap.PcapException;

import com.slytechs.jnetpcap.pro.PcapPro;
import com.slytechs.protocol.Packet;
import com.slytechs.protocol.descriptor.IpfReassembly;
import com.slytechs.protocol.pack.core.constants.IpfDescriptorType;
import com.slytechs.protocol.runtime.util.CountUnit;
import com.slytechs.protocol.runtime.util.Detail;
import com.slytechs.protocol.runtime.util.MemoryUnit;

/**
 * The example shows how to use PcapPro's IPv4 and IPv6 fragmentation reassembly
 * feature.
 */
public class Example5_IpFragmentReassembly {

	/** Bootstrap the example */
	public static void main(String[] args) throws PcapException {
		new Example5_IpFragmentReassembly().main();
	}

	/** Main example */
	void main() throws PcapException {
		final String IP_FRAGMENTED_FILE = "pcaps/ip-frags2.pcapng";

		try (PcapPro pcapPro = PcapPro.openOffline(IP_FRAGMENTED_FILE)) {

			/* Enable IP fragmentation reassembly and use many IPF options */
			pcapPro
					.enableIpf(true) // Enables both IPF reassembly and tracking
					.enableIpfReassembly(true) // Default, but this is how you disable
					.enableIpfTracking(true) // Default, but this is how you disable
					.enableIpfAttachComplete(true) // Attach only complete dgrams to last IPF
					.enableIpfAttachIncomplete(true) // Attach incomplete dgrams as well to last IPF
					.enableIpfFragments(true) // Pass through original IP fragments
					.enableIpfPassComplete(true) // Pass through new reassembled dgrams
					.enableIpfIncomplete(true) // Pass through new incomplete dgrams
					.setIpfTimeoutOnLast(true) // Otherwise only timeout on duration
					.setIpfBufferSize(1, MemoryUnit.MEGABYTES) // Total reassembly buffer size
					.setIpfTableSize(16, CountUnit.KILO) // How many hash table entries
					.setIpfMaxFragmentCount(16) // Max number of IP fragments per hash entry
					.setIpfTimeoutMilli(1200) // Timeout in system or packet time for incomplete dgrams
					.setIpfMaxDgramSize(64, MemoryUnit.KILOBYTES) // Max reassembled IP dgram size
					.useIpfPacketTimesource() // Or System timesource
			;

			pcapPro.activateIpf(); // Or Pcap.activate() if using Pcap.create(...)

			pcapPro.dispatch((Packet packet) -> {
				IpfReassembly reassemblyDesc = packet.descriptor(IpfDescriptorType.IPF_REASSEMBLY);

				if (reassemblyDesc != null)
					System.out.println(reassemblyDesc.toString(Detail.HIGH));

			});
		}
	}
}
