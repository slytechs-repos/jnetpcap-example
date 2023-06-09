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
import com.slytechs.protocol.pack.Pack;
import com.slytechs.protocol.pack.core.Ip4;
import com.slytechs.protocol.pack.core.Ip4OptRouterAlert;

/**
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 *
 */
public class Example5_ShortestForm {
	private final String PCAP_FILE = "pcaps/varied-traffic-capture-lan.pcapng";
	final Ip4 ip4 = new Ip4();
	final Ip4OptRouterAlert router = new Ip4OptRouterAlert();
	final StringBuilder out = new StringBuilder();

	public static void main(String[] args) throws PcapException {
		new Example5_ShortestForm().main();
	}

	void main() throws PcapException {
		
		Pack.loadAllDetectedPacks();
		Pack.listAllDeclaredPacks().forEach(System.out::println);

		try (var pcap = PcapPro.openOffline(PCAP_FILE)) {
//			pcap.loop(0, System.out::println);
		}

	}

}
