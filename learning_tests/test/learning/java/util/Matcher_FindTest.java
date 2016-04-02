package test.learning.java.util;

import static java.util.regex.Pattern.compile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import org.junit.Test;

public class Matcher_FindTest {
	private Matcher matcher;

	@Test
	public void find_StringLiterals_IdenticalStrings() {
		matcher = compile("foo").matcher("foo");
		
		assertTrue(matcher.find());		
		assertMatcher("foo", 0, 3);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_StringLiterals_SubsequentMatches() {
		matcher = compile("foo").matcher("foofoo");
		
		assertTrue(matcher.find());
		assertMatcher("foo", 0, 3);
		
		assertTrue(matcher.find());
		assertMatcher("foo", 3, 6);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_StringLiterals_Metacharactres() {
		matcher = compile("cat.").matcher("cats");
		
		assertTrue(matcher.find()); // because '.' means any character
		assertMatcher("cats", 0, 4);
		
		// Metacharacters:  <([{\^-=$!|]})?*+.>
	}
	
	@Test
	public void find_CharacterClasses_Or() {
		matcher = compile("[bcr]at").matcher("bat");
		assertTrue(matcher.find());		
		assertMatcher("bat", 0, 3);
		
		matcher = compile("[bcr]at").matcher("cat");
		assertTrue(matcher.find());		
		assertMatcher("cat", 0, 3);
		
		matcher = compile("[bcr]at").matcher("rat");
		assertTrue(matcher.find());		
		assertMatcher("rat", 0, 3);
	}
	
	@Test
	public void find_CharacterClasses_Negation() {
		matcher = compile("[^bcr]at").matcher("bat");
		assertFalse(matcher.find());
		
		matcher = compile("[^bcr]at").matcher("cat");
		assertFalse(matcher.find());		
		
		matcher = compile("[^bcr]at").matcher("rat");
		assertFalse(matcher.find());		
		
		matcher = compile("[^bcr]at").matcher("hat");
		assertTrue(matcher.find());		
		assertMatcher("hat", 0, 3);
	}
	
	@Test
	public void find_CharacterClasses_Ranges() {
		matcher = compile("[a-c]").matcher("a");
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		matcher = compile("[a-c]").matcher("b");
		assertTrue(matcher.find());
		assertMatcher("b", 0, 1);
		
		matcher = compile("[a-c]").matcher("c");
		assertTrue(matcher.find());
		assertMatcher("c", 0, 1);
		
		matcher = compile("[a-c]").matcher("d");
		assertFalse(matcher.find());
		
		matcher = compile("foo[1-5]").matcher("foo1");
		assertTrue(matcher.find());
		assertMatcher("foo1", 0, 4);
		
		matcher = compile("foo[1-5]").matcher("foo5");
		assertTrue(matcher.find());
		assertMatcher("foo5", 0, 4);
		
		matcher = compile("foo[1-5]").matcher("foo6");
		assertFalse(matcher.find());
		
		matcher = compile("foo[^1-5]").matcher("foo1");
		assertFalse(matcher.find());
		
		matcher = compile("foo[^1-5]").matcher("foo6");
		assertTrue(matcher.find());
		assertMatcher("foo6", 0, 4);
	}
	
	@Test
	public void find_CharacterClasses_Unions() {
		matcher = compile("[0-4[6-8]]").matcher("0");
		assertTrue(matcher.find());
		assertMatcher("0", 0, 1);
		
		matcher = compile("[0-4[6-8]]").matcher("5");
		assertFalse(matcher.find());
		
		matcher = compile("[0-4[6-8]]").matcher("6");
		assertTrue(matcher.find());
		assertMatcher("6", 0, 1);
		
		matcher = compile("[0-4[6-8]]").matcher("8");
		assertTrue(matcher.find());
		assertMatcher("8", 0, 1);
		
		matcher = compile("[0-4[6-8]]").matcher("9");
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_CharacterClasses_Intersections() {
		matcher = compile("[0-9&&[345]]").matcher("3");
		assertTrue(matcher.find());
		assertMatcher("3", 0, 1);
		
		matcher = compile("[0-9&&[345]]").matcher("4");
		assertTrue(matcher.find());
		assertMatcher("4", 0, 1);
		
		matcher = compile("[0-9&&[345]]").matcher("5");
		assertTrue(matcher.find());
		assertMatcher("5", 0, 1);
		
		matcher = compile("[0-9&&[345]]").matcher("2");
		assertFalse(matcher.find());
		
		matcher = compile("[0-9&&[345]]").matcher("6");
		assertFalse(matcher.find());
		
		matcher = compile("[2-8&&[4-6]]").matcher("3");
		assertFalse(matcher.find());
		
		matcher = compile("[2-8&&[4-6]]").matcher("4");
		assertTrue(matcher.find());
		assertMatcher("4", 0, 1);
		
		matcher = compile("[2-8&&[4-6]]").matcher("5");
		assertTrue(matcher.find());
		assertMatcher("5", 0, 1);
		
		matcher = compile("[2-8&&[4-6]]").matcher("6");
		assertTrue(matcher.find());
		assertMatcher("6", 0, 1);
		
		matcher = compile("[2-8&&[4-6]]").matcher("7");
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_CharacterClasses_Subtraction() {
		matcher = compile("[0-9&&[^345]]").matcher("2");
		assertTrue(matcher.find());
		assertMatcher("2", 0, 1);
		
		matcher = compile("[0-9&&[^345]]").matcher("3");
		assertFalse(matcher.find());
		
		matcher = compile("[0-9&&[^345]]").matcher("4");
		assertFalse(matcher.find());
		
		matcher = compile("[0-9&&[^345]]").matcher("5");
		assertFalse(matcher.find());
		
		matcher = compile("[0-9&&[^345]]").matcher("6");
		assertTrue(matcher.find());
		assertMatcher("6", 0, 1);
		
		matcher = compile("[0-9&&[^345]]").matcher("9");
		assertTrue(matcher.find());
		assertMatcher("9", 0, 1);
	}
	
	@Test
	public void find_CharacterClasses_PredefinedClasses() {
		matcher = compile(".").matcher("@"); // Any character (may or may not match line terminators)
		assertTrue(matcher.find());
		assertMatcher("@", 0, 1);
		
		matcher = compile(".").matcher("1");
		assertTrue(matcher.find());
		assertMatcher("1", 0, 1);
		
		matcher = compile(".").matcher("a");
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		matcher = compile(".").matcher("\\n");
		assertTrue(matcher.find());
		assertMatcher("\\", 0, 1); // ! Does not match '\n'. Matches '\'.
		
		matcher = compile(".").matcher("\\r");
		assertTrue(matcher.find());
		assertMatcher("\\", 0, 1); // ! Does not match '\r'.  Matches '\'
				
		matcher = compile("\\d").matcher("1"); // A digit: [0-9]
		assertTrue(matcher.find());
		assertMatcher("1", 0, 1);
		
		matcher = compile("\\d").matcher("a");
		assertFalse(matcher.find());
		
		matcher = compile("\\D").matcher("1"); // A non-digit: [^0-9]
		assertFalse(matcher.find());
		
		matcher = compile("\\D").matcher("a"); // A digit: [0-9]
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		matcher = compile("\\s").matcher(" "); // A whitespace character: [ \t\n\x0B\f\r]
		assertTrue(matcher.find());
		assertMatcher(" ", 0, 1);
		
		matcher = compile("\\S").matcher(" "); // A non-whitespace character: [^\s]
		assertFalse(matcher.find());
		
		matcher = compile("\\S").matcher("a");
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		matcher = compile("\\w").matcher("a"); // A word character: [a-zA-Z_0-9]
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		matcher = compile("\\w").matcher("!");
		assertFalse(matcher.find());
		
		matcher = compile("\\W").matcher("a"); // A non-word character: [^\w]
		assertFalse(matcher.find());
		
		matcher = compile("\\W").matcher("!");
		assertTrue(matcher.find());
		assertMatcher("!", 0, 1);
	}
	
	@Test
	public void find_Quantifiers_ZeroLengthMatches() {
		matcher = compile("a?").matcher(""); // once or not at all
		assertTrue(matcher.find());
		assertMatcher("", 0, 0);
		
		matcher = compile("a*").matcher(""); // zero or more times
		assertTrue(matcher.find());
		assertMatcher("", 0, 0);
		
		matcher = compile("a+").matcher(""); // one or more times
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_OneLengthMatches_OnceOrNotAtAll() {
		matcher = compile("a?").matcher("a");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertTrue(matcher.find());
		assertMatcher("", 1, 1);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_OneLengthMatches_ZeroOrMoreTimes() {
		matcher = compile("a*").matcher("a");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertTrue(matcher.find());
		assertMatcher("", 1, 1);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_OneLengthMatches_OneOrMoreTimes() {
		matcher = compile("a+").matcher("a");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_TwoLengthMatches_OnceOrNotAtAll() {
		matcher = compile("a?").matcher("aa");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertTrue(matcher.find());
		assertMatcher("a", 1, 2);
		
		assertTrue(matcher.find());
		assertMatcher("", 2, 2);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_TwoLengthMatches_ZeroOrMoreTimes() {
		matcher = compile("a*").matcher("aa");
		
		assertTrue(matcher.find());
		assertMatcher("aa", 0, 2);
		
		assertTrue(matcher.find());
		assertMatcher("", 2, 2);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_TwoLengthMatches_OneOrMoreTimes() {
		matcher = compile("a+").matcher("aa");
		
		assertTrue(matcher.find());
		assertMatcher("aa", 0, 2);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_AlternatingMatches_OnceOrNotAtAll() {
		matcher = compile("a?").matcher("abbaab");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertTrue(matcher.find());
		assertMatcher("", 1, 1);
		
		assertTrue(matcher.find());
		assertMatcher("", 2, 2);
		
		assertTrue(matcher.find());
		assertMatcher("a", 3, 4);
		
		assertTrue(matcher.find());
		assertMatcher("a", 4, 5);
		
		assertTrue(matcher.find());
		assertMatcher("", 5, 5);
		
		assertTrue(matcher.find());
		assertMatcher("", 6, 6);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_AlternatingMatches_ZeroOrMoreTimes() {
		matcher = compile("a*").matcher("abbaab");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertTrue(matcher.find());
		assertMatcher("", 1, 1);
		
		assertTrue(matcher.find());
		assertMatcher("", 2, 2);
		
		assertTrue(matcher.find());
		assertMatcher("aa", 3, 5);
		
		assertTrue(matcher.find());
		assertMatcher("", 5, 5);
		
		assertTrue(matcher.find());
		assertMatcher("", 6, 6);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_AlternatingMatches_OneOrMoreTimes() {
		matcher = compile("a+").matcher("abbaab");
		
		assertTrue(matcher.find());
		assertMatcher("a", 0, 1);
		
		assertTrue(matcher.find());
		assertMatcher("aa", 3, 5);
		
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_Exactly_N_Times() {
		matcher = compile("a{3}").matcher("aa");
		assertFalse(matcher.find());
		
		matcher = compile("a{3}").matcher("aaa");
		assertTrue(matcher.find());
		assertMatcher("aaa", 0, 3);
		
		matcher = compile("a{3}").matcher("aaaa");
		assertTrue(matcher.find());
		assertMatcher("aaa", 0, 3);
		
		// 9 "a"s
		matcher = compile("a{3}").matcher("aaaaaaaaaa");
		
		assertTrue(matcher.find());
		assertMatcher("aaa", 0, 3);
		
		assertTrue(matcher.find());
		assertMatcher("aaa", 3, 6);
		
		assertTrue(matcher.find());
		assertMatcher("aaa", 6, 9);
	}
	
	@Test
	public void find_Quantifiers_AtLeast_N_Times() {
		matcher = compile("a{3,}").matcher("aaaaaaaaa");
		assertTrue(matcher.find());
		assertMatcher("aaaaaaaaa", 0, 9);
	}
	
	@Test
	public void find_Quantifiers_AtLeast_N_TimesButNoMoreThan_M_Times() {
		matcher = compile("a{3,6}").matcher("aaaaaaaaa");
		
		assertTrue(matcher.find());
		assertMatcher("aaaaaa", 0, 6);
		
		assertTrue(matcher.find());
		assertMatcher("aaa", 6, 9);
	}
	
	@Test
	public void find_Quantifiers_CapturingGroups() {
		matcher = compile("(dog){3}").matcher("dogdogdogdogdogdogdogdogdog");
		
		assertTrue(matcher.find());
		assertMatcher("dogdogdog", 0, 9);
		
		assertTrue(matcher.find());
		assertMatcher("dogdogdog", 9, 18);
	}
	
	@Test
	public void find_Quantifiers_ApplyingToLastCharacter() {
		matcher = compile("dog{3}").matcher("dogdogdogdogdogdogdogdogdog");
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_Quantifiers_ApplyingToCharacterClass() {
		matcher = compile("[abc]{3}").matcher("abccabaaaccbbbc");
		
		assertTrue(matcher.find());
		assertMatcher("abc", 0, 3);
		
		assertTrue(matcher.find());
		assertMatcher("cab", 3, 6);
		
		assertTrue(matcher.find());
		assertMatcher("aaa", 6, 9);
		
		assertTrue(matcher.find());
		assertMatcher("ccb", 9, 12);
		
		assertTrue(matcher.find());
		assertMatcher("bbc", 12, 15);
	}
	
	@Test
	public void find_Quantifiers_Greedy() {
		matcher = compile(".*foo").matcher("xfooxxxxxxfoo");
		
		assertTrue(matcher.find());
		assertMatcher("xfooxxxxxxfoo", 0, 13);
	}
	
	@Test
	public void find_Quantifiers_Relucant() {
		matcher = compile(".*?foo").matcher("xfooxxxxxxfoo");
		
		assertTrue(matcher.find());
		assertMatcher("xfoo", 0, 4);
		
		assertTrue(matcher.find());
		assertMatcher("xxxxxxfoo", 4, 13);
	}
	
	@Test
	public void find_Quantifiers_Possessive() {
		matcher = compile(".*+foo").matcher("xfooxxxxxxfoo");
		assertFalse(matcher.find());
	}
	
	@Test
	public void find_CapturingGroups_Numbering() {
		matcher = compile("((A)(B(C)))").matcher("ABC");
		
		assertTrue(matcher.find());
		assertEquals(4, matcher.groupCount());
		
		assertEquals("ABC", matcher.group(0));
		assertEquals(0, matcher.start(0));
		assertEquals(3, matcher.end(0));
		
		assertEquals("ABC", matcher.group(1));
		assertEquals(0, matcher.start(1));
		assertEquals(3, matcher.end(1));
		
		assertEquals("A", matcher.group(2));
		assertEquals(0, matcher.start(2));
		assertEquals(1, matcher.end(2));
		
		assertEquals("BC", matcher.group(3));
		assertEquals(1, matcher.start(3));
		assertEquals(3, matcher.end(3));
		
		assertEquals("C", matcher.group(4));
		assertEquals(2, matcher.start(4));
		assertEquals(3, matcher.end(4));
	}
	
	private void assertMatcher(String group, int start, int end) {
		assertEquals(group, matcher.group());
		assertEquals(start, matcher.start());
		assertEquals(end, matcher.end());
	}

}
