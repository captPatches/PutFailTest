package org.jboss.narayana.kvstore.infinispan.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Test;

public class BehaviourTest {

	private final String CFG_FILE = "generic-test-cfg.xml";
	private final String CACHE_NAME = "dis";

	@Test
	public void test() throws IOException {

		DefaultCacheManager manager = new DefaultCacheManager(CFG_FILE);
		Cache<String, String> cache = manager.getCache(CACHE_NAME);

		assertEquals("numOwners Should be 5: ", 5, cache.getAdvancedCache()
				.getCacheConfiguration().clustering().hash().numOwners());

		assertEquals("Cache Should be distributed: ", true, cache
				.getAdvancedCache().getCacheConfiguration().clustering()
				.cacheMode().isDistributed());

		assertEquals("Cache Should not be Replicated", false, cache
				.getAdvancedCache().getCacheConfiguration().clustering()
				.cacheMode().isReplicated());

		assertEquals("numOwners > clusterSize: ", true, (cache
				.getAdvancedCache().getCacheConfiguration().clustering().hash()
				.numOwners() >= manager.getClusterSize()));

		assertNull("Cache Empty", cache.get("ned"));

		cache.put("ned", "stark");

		assertEquals("Check Put Worked: ", "stark", cache.get("ned"));
	}

}
