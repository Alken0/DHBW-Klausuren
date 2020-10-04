package test_lib;

import lib.CL_Random;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CL_RandomTest {

	@Test
	public void getRandom() {
		var min = 0;
		var max = 5;
		for (int i = 0; i < 100; i++) {
			var result = CL_Random.getInclusiveRandom(min, max);
			assertTrue(result >= min);
			assertTrue(result <= max);
		}
	}

	@Test
	public void getExclusiveDoubleRandom() {
		var min = 1d;
		var max = 5d;
		for (int i = 0; i < 100; i++) {
			var result = CL_Random.getExclusiveRandom(min, max);
			assertTrue(result >= min);
			assertTrue(result < max);
		}
	}

	@Test
	public void getExclusiveIntRandom() {
		var min = 1;
		var max = 5;
		for (int i = 0; i < 100; i++) {
			var result = CL_Random.getExclusiveRandom(min, max);
			assertTrue(result >= min);
			assertTrue(result < max);
		}
	}
}
