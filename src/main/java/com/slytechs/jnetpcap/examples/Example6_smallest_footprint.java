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

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 */
public class Example6_smallest_footprint {
	private final String PCAP_FILE = "pcaps/LAN-1.pcapng";

	public static void main(String[] args) throws PcapException {
		new Example6_smallest_footprint().main();
	}

	void main() throws PcapException {

		try (var pcap = PcapPro.openOffline("capture.pcapng")) {
			pcap.dispatch(System.out::println);
		}

	}
}
