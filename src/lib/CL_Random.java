package lib;

import java.util.concurrent.ThreadLocalRandom;

public class CL_Random {

	public static int getInclusiveRandom(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static int getExclusiveRandom(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

	public static double getExclusiveRandom(double min, double max) {
		return ThreadLocalRandom.current().nextDouble(min, max);
	}
}
