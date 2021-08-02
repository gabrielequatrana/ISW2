package it.uniroma2.dicii.isw2.jcs.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.apache.jcs.JCS;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RemovalTestUtil {
	
	// Test parameters
	private int start;
	private int end;
	private boolean check;
	
	// JCS instance
	private JCS jcs;
	
	// Test environment
	private enum TestType {TYPE1, TYPE2, TYPE3}
	private TestType testType;

	/*
	 * Test parameters obtained from Domain Partitioning
	 */
	@Parameters
	public static Collection<Object[]> getTestParameters() {
		return Arrays.asList(new Object[][] {
			{0, 200, false, TestType.TYPE1},
			{601, 700, false, TestType.TYPE1},
			{701, 800, false, TestType.TYPE1},
			{300, 400, false, TestType.TYPE2},
			{401, 600, false, TestType.TYPE2},
			{0, 1000, false, TestType.TYPE3},
		});
	}
	
	public RemovalTestUtil(int start, int end, boolean check, TestType testType) {
		this.start = start;
		this.end = end;
		this.check = check;
		this.testType = testType;
	}
	
	/*
	 * Environment configuration
	 */
	@Before
	public void configureEnvironment() throws Exception {
		JCS.setConfigFilename("/cache.ccf");
		this.jcs = JCS.getInstance("testCache1");
	}
	
	/*
	 * Clean the environment
	 */
	@After
	public void releaseResources() throws Exception {
		this.jcs.clear();
		this.jcs.dispose();
	}

	/**
     * Adds elements in the range specified and then removes them using the
     * categorical or substring removal method.
     *
     * @param start
     * @param end
     *
     * @exception Exception
     */
	@Test
	public void runTestPutThenRemoveCategorical() throws Exception {
		
		Assume.assumeTrue(testType == TestType.TYPE1);
		
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
		System.out.println(jcs.getStats()+"\n");
	}

	/**
     * Put items in the cache in this key range. Can be used to verify that
     * concurrent operations are not effected by things like hierchical removal.
     *
     * @param start
     *            int
     * @param end
     *            int
     * @throws Exception
     */
	@Test
	public void runPutInRange() throws Exception {
		
		Assume.assumeTrue(testType == TestType.TYPE2);

		for (int i = start; i <= end; i++) {
			jcs.put(i + ":key", "data" + i);
		}

		for (int i = end; i >= start; i--) {
			String res = (String) jcs.get(i + ":key");
			assertNotNull("[" + i + ":key] should not be null", res);
		}
	}

	/**
     * Just get from start to end.
     *
     * @param start
     *            int
     * @param end
     *            int
     * @param check
     *            boolean -- check to see if the items are in the cache.
     * @throws Exception
     */
	@Test
	public void runGetInRange() throws Exception {
		
		Assume.assumeTrue(testType == TestType.TYPE3);

		// don't care if they are found
		for (int i = end; i >= start; i--) {
			String res = (String) jcs.get(i + ":key");
			if (check && res == null) {
				assertNotNull("[" + i + ":key] should not be null", res);
			}
		}
	}
}
