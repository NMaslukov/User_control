package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {
	public static String t(String a) {
		return a = a + 0;
	}

	Function<String, String> f;

	public static void main(String[] args) {
		List<String> l = new ArrayList<>();
		l.add("1");
		l.add("2");
		l.add("3");
		l.add("4");
		System.out.println(l.stream().map(zalupa -> zalupa + 1).collect(Collectors.toList()));
	}

}
