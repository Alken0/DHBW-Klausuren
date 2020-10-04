package test_lib;

import lib.CL_List;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CL_ListTest {

	@Test
	public void shuffle() {
		// could fail due to randomness
		var input = Arrays.asList(1, 2);
		var firstElements = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			var firstElement = CL_List.shuffle(input).get(0);
			assertEquals(Arrays.asList(1, 2), input);
			firstElements.add(firstElement);
		}
		assertTrue(firstElements.contains(1));
		assertTrue(firstElements.contains(2));
	}

	@Test
	public void reverse() {
		var input = Arrays.asList(5, 3, 1, 2, 4);
		var result = CL_List.reverse(input);
		assertEquals(Arrays.asList(5, 3, 1, 2, 4), input);
		assertEquals(Arrays.asList(4, 2, 1, 3, 5), result);
	}

	@Test
	public void sortNoParameter() {
		var input = Arrays.asList(5, 3, 1, 2, 4);
		var result = CL_List.sort(input);
		assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
	}

	@Test
	public void sortWithCompareFunction() {
		var input = Arrays.asList("c", "ab", "lklaf", "sfe");
		var result = CL_List.sort(input, String::length);
		assertEquals(Arrays.asList("c", "ab", "sfe", "lklaf"), result);
	}

	@Test
	public void filter() {
		var input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		var result = CL_List.filter(input, i -> i < 5);
		assertEquals(Arrays.asList(1, 2, 3, 4), result);
	}
}
