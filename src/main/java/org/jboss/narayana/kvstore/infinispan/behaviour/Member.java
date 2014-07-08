package org.jboss.narayana.kvstore.infinispan.behaviour;

import org.infinispan.manager.DefaultCacheManager;

public class Member {

	private static final String CFG_FILE = "generic-test-cfg.xml";
	private static final String CACHE_NAME = "dis";
	
	public static void main(String[] args) {
		
		System.setProperty("java.net.preferIPv4Stack", "true");
		
		try {
			new DefaultCacheManager(CFG_FILE).getCache(CACHE_NAME);
			System.out.println("Node Started Successfully");
		} catch (Exception e){
			System.err.println("Node Failed");
		}

	}

}
