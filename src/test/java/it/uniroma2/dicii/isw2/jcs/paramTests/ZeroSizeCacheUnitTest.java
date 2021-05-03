package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)
public class ZeroSizeCacheUnitTest {

	/** number to get each loop */
	private int items;
	
	private JCS jcs;
	private static CompositeCacheManager ccm;
	
	@Parameters
	public static Collection<Integer[]> getTestParameters() {
		return Arrays.asList(new Integer[][] {
			{1},
			{0},
			{-1},
			{10000},
			{-10000}
		});
	}
	
	public ZeroSizeCacheUnitTest(int items) {
		this.items = items;
	}

	@Before
	public void setUp() throws Exception {
		ZeroSizeCacheUnitTest.ccm = CompositeCacheManager.getUnconfiguredInstance();
		Properties props = new Properties();
		
		// VEDERE COME USERE ccf file pd
		props.put("jcs.default", "");
		props.put("jcs.default.cacheattributes", "org.apache.jcs.engine.CompositeCacheAttributes");
		props.put("jcs.default.cacheattributes.MaxObjects", "0");
		props.put("jcs.default.cacheattributes.MemoryCacheName", "org.apache.jcs.engine.memory.lru.LRUMemoryCache");
		props.put("jcs.default.cacheattributes.UseMemoryShrinker", "true");
		props.put("jcs.default.cacheattributes.MaxMemoryIdleTimeSeconds", "3600");
		props.put("jcs.default.cacheattributes.ShrinkerIntervalSeconds", "1");
		props.put("jcs.default.elementattributes", "org.apache.jcs.engine.ElementAttributes");
		props.put("jcs.default.elementattributes.IsEternal", "false");
		props.put("jcs.default.elementattributes.MaxLifeSeconds", "600");
		props.put("jcs.default.elementattributes.IdleTime", "1800");
		props.put("jcs.default.elementattributes.IsSpool", "true");
		props.put("jcs.default.elementattributes.IsRemote", "true");
		props.put("jcs.default.elementattributes.IsLateral", "true");
		
		ccm.configure(props);
		
		//JCS.setConfigFilename("/TestZeroSizeCache.ccf");
		JCS.getInstance("testCache1");
	}
	
	@After
	public void cleanUp() throws Exception {
		this.jcs.clear();
	}

	@Test
	public void testPutGetRemove() throws Exception {
		this.jcs = JCS.getInstance("testCache1");

		for (int i = 0; i <= items; i++) {
			jcs.put(i + ":key", "data" + i);
		}

		// all the gets should be null
		for (int i = items; i >= 0; i--) {
			String res = (String) jcs.get(i + ":key");
			if (res == null) {
				assertNull("[" + i + ":key] should be null", res);
			}
		}

		// test removal, should be no exceptions
		jcs.remove("300:key");

		// allow the shrinker to run
		Thread.sleep(500);

		// do it again.
		for (int i = 0; i <= items; i++) {
			jcs.put(i + ":key", "data" + i);
		}

		for (int i = items; i >= 0; i--) {
			String res = (String) jcs.get(i + ":key");
			if (res == null) {
				assertNull("[" + i + ":key] should be null", res);
			}
		}

		System.out.println(jcs.getStats());
	}
}