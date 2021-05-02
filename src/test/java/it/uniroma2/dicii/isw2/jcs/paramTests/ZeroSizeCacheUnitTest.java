package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.*;

import org.apache.jcs.JCS;
import org.junit.Before;
import org.junit.Test;

public class ZeroSizeCacheUnitTest {

	/** number to get each loop */
	private static int items = 20000;

	@Before
	public void setUp() throws Exception {
		JCS.setConfigFilename("/TestZeroSizeCache.ccf");
		JCS.getInstance("testCache1");
	}

	@Test
	public void testPutGetRemove() throws Exception {
		JCS jcs = JCS.getInstance("testCache1");

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