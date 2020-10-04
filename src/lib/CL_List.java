package lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CL_List {

	public static <T> List<T> shuffle(List<T> list) {
		var shuffledList = new ArrayList<>(list);
		Collections.shuffle(shuffledList);
		return shuffledList;
	}

	public static <T> List<T> reverse(List<T> list) {
		var reversedList = new ArrayList<>(list);
		Collections.reverse(reversedList);
		return reversedList;
	}

	// compareFunction example: String::length
	public static <T, U extends Comparable<? super U>> List<T> sort(List<T> list, Function<T, U> compareFunction) {
		return list.stream()
				.sorted(Comparator.comparing(compareFunction))
				.collect(Collectors.toList());
	}

	public static <T> List<T> sort(List<T> list) {
		return list.stream()
				.sorted()
				.collect(Collectors.toList());
	}

	// filterFunction example: i -> i < 3
	public static <T> List<T> filter(List<T> list, Predicate<T> filterFunction) {
		return list.stream()
				.filter(filterFunction)
				.collect(Collectors.toList());
	}

	// mapFunction example: i -> i * 2
	public static <T> List<T> map(List<T> list, Function<T, ? extends T> mapFunction){
		return list.stream()
				.map(mapFunction)
				.collect(Collectors.toList());
	}
}
