package org.self.learn.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.self.learn.DuplicateKeyFoundException;
import org.self.learn.NoSuchKeyException;
import org.self.learn.SimpleMap;

@RunWith(BlockJUnit4ClassRunner.class)
public class SimpleMapTest {
	private SimpleMap<Integer,String> map;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init() {
		map = new SimpleMap<>();
	}	
	
	@Test
	public void testEmptyMapCreation() {
		assertThat(0, is(equalTo(map.size())));
		assertEquals(0, map.size());
	}
	
	@Test
	public void testNonEmptyMap() throws DuplicateKeyFoundException {
		map.save(1, "Priyan");
		assertThat(0, is(not(equalTo(map.size()))));
	}
	
	@Test
	public void testSize() throws DuplicateKeyFoundException {
		map.save(1, "Priyan");
		assertThat(1, is(equalTo(map.size())));
		map.save(2, "Priyadarshan");
		assertThat(2, is(equalTo(map.size())));
		map.save(10, "Priyan Parida");
		assertThat(3, is(equalTo(map.size())));
	}
	
	@Test
	public void testSingleSaveKeyValuePair() throws NoSuchKeyException, DuplicateKeyFoundException {
		map.save(1, "Priyan");
		assertThat("Priyan",is(equalTo(map.get(1))));
	}
	
	@Test
	public void testMultipleSaveKeyValuePair() throws NoSuchKeyException, DuplicateKeyFoundException {
		map.save(1, "Priyan");
		map.save(2, "Priyadarshan");
		assertThat("Priyadarshan",is(equalTo(map.get(2))));
		assertThat("Priyan",is(equalTo(map.get(1))));
	}
	
	@Test
	public void testContainsKey() throws DuplicateKeyFoundException {
		map.save(1, "Priyan");
		assertThat(true, is(equalTo(map.containsKey(1))));
		assertThat(true, is(not(equalTo(map.containsKey(35)))));
		assertFalse(map.containsKey(35));
	}
	
	@Test
	public void testNoSuchKeyException() throws NoSuchKeyException, DuplicateKeyFoundException {
		map.save(1, "Priyan");
		exception.expect(NoSuchKeyException.class);
		map.get(2);
	}
	
	@Test
	public void testContainsValue() throws DuplicateKeyFoundException {
		map.save(1, "Priyan");
		map.save(2, "Priyan");
		map.save(3, "Priyadarshan");
		assertTrue(map.containsValue("Priyan"));
		assertTrue(map.containsValue("Priyadarshan"));
		assertFalse(map.containsValue("Priyan Parida"));
	}
	
	@Test
	public void testDuplicateKeyFoundException() throws NoSuchKeyException {
		try {
			map.save(1, "Priyan");
			map.save(1, "Priyadarshan");
		} catch (Exception e) {
			assertThat(e,is(instanceOf(DuplicateKeyFoundException.class)));
		}
		
		assertEquals("Priyan", map.get(1));
	}
	
	@Test
	public void testNullKeyAndValues() throws DuplicateKeyFoundException, NoSuchKeyException {
		map.save(null, null);
		map.save(1, null);
		assertTrue(map.containsKey(null));
		try {
			map.save(null, "Priyan");
		} catch (Exception e) {
			assertThat(e,is(instanceOf(DuplicateKeyFoundException.class)));
		}
		assertNull(map.get(null));
		assertTrue(map.containsValue(null));
	}
	
	@Test
	public void testRemove() throws DuplicateKeyFoundException, NoSuchKeyException {
		map.save(1, "Priyan");
		map.save(2, "Priyadarshan");
		map.remove(2);
		assertThat(false,is(equalTo(map.containsKey(2))));
		assertThat(false,is(equalTo(map.containsValue("Priyadarshan"))));
		assertThat(1, is(equalTo(map.size())));
		exception.expect(NoSuchKeyException.class);
		map.get(2);
	}
	
	@Test
	public void testDynamicMapSize() throws DuplicateKeyFoundException, NoSuchKeyException {
		map.save(1, "Priyan-1");
		map.save(2, "Priyan-2");
		map.save(3, "Priyan-3");
		map.save(4, "Priyan-4");
		map.save(5, "Priyan-5");
		map.save(6, "Priyan-6");
		assertThat("Priyan-6", is(equalTo(map.get(6))));
		assertThat("Priyan-5", is(equalTo(map.get(5))));
		assertThat("Priyan-4", is(equalTo(map.get(4))));
		assertThat("Priyan-3", is(equalTo(map.get(3))));
		assertThat("Priyan-2", is(equalTo(map.get(2))));
		assertThat("Priyan-1", is(equalTo(map.get(1))));
		assertEquals(6,map.size());
	}
}