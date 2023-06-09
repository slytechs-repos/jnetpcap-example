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
package com.slytechs.jnetpcap.examples.usecase;

import org.jnetpcap.PcapException;

import com.slytechs.jnetpcap.pro.PcapPro;
import com.slytechs.protocol.pack.core.Ip4;
import com.slytechs.protocol.pack.core.Ip6;
import com.slytechs.protocol.runtime.util.Detail;

/**
 * Different use-cases of dealing with various IP related options (v4 and v6).
 * 
 * @author Sly Technologies Inc
 * @author repos@slytechs.com
 */
public class UsecaseIpOptions {

	static final String FILENAME = "pcaps/ipv6-udp-fragmented.pcap";

	public static void main(String[] args) throws PcapException {
		new UsecaseIpOptions().usecase1_listIpOptions();
	}

	void usecase1_listIpOptions() throws PcapException {
		try (var pcap = PcapPro.openOffline(FILENAME)) {

			Ip4 ip4 = new Ip4();
			Ip6 ip6 = new Ip6();

			pcap.dispatch(packet -> {
				if (packet.hasHeader(ip4)) {

				}

				if (packet.hasHeader(ip6)) {
				}
				
				System.out.println(packet.descriptor().toString(Detail.HIGH));
			});

		}
	}

}
