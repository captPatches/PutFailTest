package org.jboss.narayana.kvstore.infinispan.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Before;
import org.junit.Test;

public class BehaviourTest {

	private final String CFG_FILE = "generic-test-cfg.xml";
	private final String CACHE_NAME = "dis";

	@Before
	public void setup() {
		System.setProperty("java.net.preferIPv4Stack", "true");
	}
	
	@Test
	public void test() throws IOException {

		DefaultCacheManager manager = new DefaultCacheManager(CFG_FILE);
		Cache<String, String> cache = manager.getCache(CACHE_NAME);

		// send cache config options to standard out:
		System.out.println("\n\nCluster Size: " + manager.getClusterSize());
		System.out.println("numOwners: "
				+ cache.getAdvancedCache().getCacheConfiguration().clustering()
						.hash().numOwners()+"\n");
		
		// Assure Cache is setup as stated
		assertEquals("numOwners Should be 5: ", 5, cache.getAdvancedCache()
				.getCacheConfiguration().clustering().hash().numOwners());

		assertEquals("Cache Should be distributed: ", true, cache
				.getAdvancedCache().getCacheConfiguration().clustering()
				.cacheMode().isDistributed());

		assertEquals("Cache Should not be Replicated", false, cache
				.getAdvancedCache().getCacheConfiguration().clustering()
				.cacheMode().isReplicated());

		// perform simple test and check it is true.
		assertTrue("numOwners > clusterSize: ",(cache
				.getAdvancedCache().getCacheConfiguration().clustering().hash()
				.numOwners() > manager.getClusterSize()));

		assertNull("Cache Not Empty", cache.get("ned"));
		
		// test PUT
		cache.put("ned", "stark");
	
		// assertEquals("Check Put Worked: ", "stark", cache.get("ned"));
		assertNull("<PUT> should have failed: ", cache.get("ned"));
		
		manager.stop();
	}
}
