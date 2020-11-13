package com.catascopic.template.expr;

import static com.catascopic.template.value.Values.camelToSeparator;
import static com.catascopic.template.value.Values.range;
import static com.catascopic.template.value.Values.separatorToCamel;
import static com.catascopic.template.value.Values.slice;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.catascopic.template.value.Values;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

public class FunctionsTest {

	@Test
	public void testSeparatorToCamel() {
		Assert.assertEquals(camelToSeparator(""), "");
		Assert.assertEquals(camelToSeparator("foo"), "foo");
		Assert.assertEquals(camelToSeparator("fooBar"), "foo_bar");
		Assert.assertEquals(camelToSeparator("fooBarBaz"), "foo_bar_baz");
	}

	@Test
	public void testCamelToSeparator() {
		Assert.assertEquals(separatorToCamel(""), "");
		Assert.assertEquals(separatorToCamel("foo"), "foo");
		Assert.assertEquals(separatorToCamel("foo_bar"), "fooBar");
		Assert.assertEquals(separatorToCamel("foo_bar_baz"), "fooBarBaz");
	}

	@Test
	public void testRange() {
		Assert.assertEquals(range(5), Arrays.asList(0, 1, 2, 3, 4));
		Assert.assertEquals(range(3, 6), Arrays.asList(3, 4, 5));
		Assert.assertEquals(range(2, 0), Collections.emptyList());
		Assert.assertEquals(range(0, 2, -1), Collections.emptyList());
		Assert.assertEquals(range(2, 0, -1), Arrays.asList(2, 1));
		Assert.assertEquals(range(0, 5, 2), Arrays.asList(0, 2, 4));
		Assert.assertEquals(range(-5, 0, 2), Arrays.asList(-5, -3, -1));
		Assert.assertEquals(range(1, 7, 3), Arrays.asList(1, 4));
		Assert.assertEquals(range(0, 30, 5),
				Arrays.asList(0, 5, 10, 15, 20, 25));
	}

	@Test
	public void testSlice() {
		Assert.assertEquals(slice("abcde", 0, 2, 1), "ab");
		Assert.assertEquals(slice("abcde", null, 2), "ab");
		Assert.assertEquals(slice("abcde", 5, 5), "");
		Assert.assertEquals(slice("abcde", 5, 0), "");
		Assert.assertEquals(slice("abcde", -1, 0, -1), "edcb");
		Assert.assertEquals(slice("abcde", -1, -6, -1), "edcba");
		Assert.assertEquals(slice("abcde", null, null, -1), "edcba");
		Assert.assertEquals(slice("abcde", null, null, null), "abcde");
		Assert.assertEquals(slice("abcde", null, null, 2), "ace");
		Assert.assertEquals(slice("abcde", null, null, -2), "eca");
		Assert.assertEquals(slice("abcde", -2, null, -2), "db");
		Assert.assertEquals(slice("abcde", -2), "de");
		Assert.assertEquals(slice(Arrays.asList(1, 2, 3, 4), 2),
				Arrays.asList(3, 4));

	}

	@Test
	public void testUneval() {
		System.out.println(Values.uneval((Object) ImmutableMap.of("what",
				Arrays.asList(5, "\\5"))));
	}

	@Test
	public void testSplitLInes() {
		assertIterablesEqual(ImmutableList.of(""), Values.splitLines(""));
		assertIterablesEqual(ImmutableList.of("abc"), Values.splitLines("abc"));
		assertIterablesEqual(ImmutableList.of("a", "b", "c"), Values.splitLines("a\nb\nc"));
		assertIterablesEqual(ImmutableList.of("ab", "c", ""), Values.splitLines("ab\rc\r"));
		assertIterablesEqual(ImmutableList.of("a", "bc", "d",
				"e", "", ""), Values.splitLines("a\r\nbc\rd\ne\n\r"));
	}

	static <E> void assertIterablesEqual(Iterable<E> expected, Iterable<E> actual) {
		if (!Iterables.elementsEqual(expected, actual)) {
			throw new AssertionError(String.format("expected %s but was %s",
					Iterables.toString(expected),
					Iterables.toString(actual)));
		}
	}

}
