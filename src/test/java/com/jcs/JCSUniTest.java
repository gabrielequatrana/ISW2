package com.jcs;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class JCSUniTest {
	
	Random random = new Random();
	
	@Parameters
	public static Collection<Integer[]> getTestParameters() {
		return Arrays.asList(new Integer[][] {
			{},
			{},
			{},
		});
	}
	
	public JCSUniTest() {
		
	}
	
	@Test
	public void t() throws CacheException {
		/*JCS jcs = JCS.getInstance("testCache1");
		
		LinkedList<HashMap<?, ?>> list = buildList();
		
		jcs.put("some:key", list);
		
		assertEquals(list, jcs.get("some:key"));*/
	}
	
	private LinkedList<HashMap<?, ?>> buildList() {
        LinkedList<HashMap<?, ?>> list = new LinkedList<HashMap<?, ?>>();

        for (int i = 0; i < 100; i++){
            list.add(buildMap());
        }

        return list;
    }
	
	private HashMap<String, String> buildMap() {
        HashMap<String, String> map = new HashMap<String, String>();

        byte[] keyBytes = new byte[32];
        byte[] valBytes = new byte[128];

        for (int i = 0; i < 10; i++) {
            random.nextBytes(keyBytes);
            random.nextBytes(valBytes);

            map.put(new String(keyBytes), new String(valBytes));
        }

        return map;
    }

}
