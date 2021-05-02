package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.*;

import org.apache.jcs.JCS;
import org.junit.Test;

public class RemovalTestUtil {

	@Test
	public void runTestPutThenRemoveCategorical(int start, int end) throws Exception {
		JCS jcs = JCS.getInstance("testCache1");

		for (int i = start; i <= end; i++) {
			jcs.put(i + ":key", "data" + i);
		}

		for (int i = end; i >= start; i--) {
			String res = (String) jcs.get(i + ":key");
			if (res == null) {
				assertNotNull("[" + i + ":key] should not be null", res);
			}
		}

		System.out.println("Confirmed that " + (end - start) + " items could be found");

		for (int i = start; i <= end; i++) {
			jcs.remove(i + ":");
			assertNull(jcs.get(i + ":key"));
		}

		System.out.println("Confirmed that " + (end - start) + " items were removed");
		System.out.println(jcs.getStats());
	}

	@Test
	public void runPutInRange(int start, int end) throws Exception {
		JCS jcs = JCS.getInstance("testCache1");

		for (int i = start; i <= end; i++) {
			jcs.put(i + ":key", "data" + i);
		}

		for (int i = end; i >= start; i--) {
			String res = (String) jcs.get(i + ":key");
			if (res == null) {
				assertNotNull("[" + i + ":key] should not be null", res);
			}
		}
	}

	@Test
	public void runGetInRange(int start, int end, boolean check) throws Exception {
		JCS jcs = JCS.getInstance("testCache1");

		// don't care if they are found
		for (int i = end; i >= start; i--) {
			String res = (String) jcs.get(i + ":key");
			if (check && res == null) {
				assertNotNull("[" + i + ":key] should not be null", res);
			}

		}

	}
}
