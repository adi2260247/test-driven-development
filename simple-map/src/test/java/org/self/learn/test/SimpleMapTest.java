package org.self.learn.test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.self.learn.DuplicateKeyFoundException;
import org.self.learn.NoSuchKeyFoundException;
import org.self.learn.SimpleMap;

@RunWith(BlockJUnit4ClassRunner.class)
public class SimpleMapTest {
	private SimpleMap<Integer, String> map;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void init() {
		map = new SimpleMap<>();
	}

	@Test
	public void itShouldBeAbleToInitializeAnEmptyMapWithSizeZero() {
		assertThat(map.size(), is(equalTo(0)));
	}

	@Test
	public void itShouldReturnSizeGreaterThanZeroUponAddingKeyValuePair() 
			throws DuplicateKeyFoundException {
		map.add(1, "Priyan");
		assertThat(map.size(), is(greaterThan(0)));
	}

	@Test
	public void itShouldBeAbleToReturnSizeOfTheMap() 
			throws DuplicateKeyFoundException {
		map.add(1, "Priyan");
		assertThat(map.size(), is(equalTo(1)));
		map.add(5, "Priyadarshan");
		assertThat(map.size(), is(equalTo(2)));
		map.add(200, "Priyan Parida");
		assertThat(map.size(), is(equalTo(3)));
	}

	@Test
	public void itShouldSaveAKeyAndValuePair() throws Exception {
		map.add(1, "Priyan");
		assertThat(map.get(1), is(equalTo("Priyan")));
	}

	@Test
	public void itShouldSaveMultipleKeyAndValuePairs() 
			throws NoSuchKeyFoundException, DuplicateKeyFoundException {
		map.add(1, "Priyan");
		map.add(2, "Priyadarshan");
		assertThat(map.get(2), is(equalTo("Priyadarshan")));
		assertThat(map.get(1), is(equalTo("Priyan")));
	}

	@Test
	public void itShouldIndicateIfGivenKeyIsPartOfTheMap() 
			throws DuplicateKeyFoundException {
		map.add(1, "Priyan");
		assertTrue(map.containsKey(1));
		assertFalse(map.containsKey(500));
		assertThat(map.containsKey(51), is(not(equalTo(true))));
	}

	@Test
	public void itShouldThrowNoSuchKeyFoundException() throws Exception {
		map.add(1, "Priyan");
		exception.expect(NoSuchKeyFoundException.class);
		map.get(45);
	}

	@Test
	public void itShouldIndicateIfGivenValueIsPartOfTheMap() throws DuplicateKeyFoundException {
		map.add(1, "Priyan");
		map.add(2, "Priyan");
		map.add(3, "Priyadarshan");
		assertTrue(map.containsValue("Priyan"));
		assertTrue(map.containsValue("Priyadarshan"));
		assertFalse(map.containsValue("Priyan Parida"));
	}

	@Test(expected = DuplicateKeyFoundException.class)
	public void itShouldThrowAnExceptionWhenDuplicateKeyIsEntered() 
			throws NoSuchKeyFoundException, DuplicateKeyFoundException {
		map.add(1, "Priyan");
		map.add(1, "Priyadarshan");
	}
	
	@Test
	public void itShouldDiscardEntryWithDuplicateKeyInAdditionToThrowingException() 
			throws NoSuchKeyFoundException {
		try {
			map.add(1, "Priyan");
			map.add(1, "Priyadarshan");
		} catch (Exception e) {
			assertThat(e,is(instanceOf(DuplicateKeyFoundException.class)));
		}
		
		assertThat(map.get(1), is(not(equalTo("Priyadarshan"))));
	}
	
	@Test
	public void itShouldBeAbleToAcceptANullKeyAndNullValues() 
			throws DuplicateKeyFoundException, NoSuchKeyFoundException {
		map.add(null, null);
		map.add(1, null);
		assertTrue(map.containsKey(null));
		try {
			map.add(null, "Priyan");
		} catch (Exception e) {
			assertThat(e,is(instanceOf(DuplicateKeyFoundException.class)));
		}
		assertNull(map.get(null));
		assertThat(map.containsKey(null), is(equalTo(true)));
		assertThat(map.containsValue(null), is(equalTo(true)));
	}
	
	@Test
	public void itShouldAllowToRemoveKeyValuePairGivenCorrespondingKey() 
			throws DuplicateKeyFoundException, NoSuchKeyFoundException {
		map.add(1, "Priyan");
		map.add(2, "Priyadarshan");
		map.remove(2);
		assertThat(map.containsKey(2),is(equalTo(false)));
		assertThat(map.containsValue("Priyadarshan"),is(equalTo(false)));
		assertThat(map.size(), is(equalTo(1)));
	}
	
	@Test(expected = NoSuchKeyFoundException.class)
	public void removeShouldThrowNoSuchKeyFoundExceptionWhenMapIsEmpty() 
			throws NoSuchKeyFoundException {
		map.remove(100);
	}
	
	@Test(expected = NoSuchKeyFoundException.class)
	public void removeShouldThrowNoSuchKeyFoundExceptionWhenNoSuchKeyExists() 
			throws DuplicateKeyFoundException, NoSuchKeyFoundException {
		map.add(1, "Priyan");
		map.remove(100);
	}
	
	@Test
	public void itShouldGrowInSzieAtRuntimeBasedOnNeed() 
			throws DuplicateKeyFoundException, NoSuchKeyFoundException {
		map.add(1, "Priyan-1");
		map.add(2, "Priyan-2");
		map.add(3, "Priyan-3");
		map.add(4, "Priyan-4");
		map.add(5, "Priyan-5");
		map.add(6, "Priyan-6");
		assertThat(map.get(6), is(equalTo("Priyan-6")));
		assertThat(map.get(5), is(equalTo("Priyan-5")));
		assertThat(map.get(4), is(equalTo("Priyan-4")));
		assertThat(map.get(3), is(equalTo("Priyan-3")));
		assertThat(map.get(2), is(equalTo("Priyan-2")));
		assertThat(map.get(1), is(equalTo("Priyan-1")));
		assertEquals(6,map.size());
	}
	
}