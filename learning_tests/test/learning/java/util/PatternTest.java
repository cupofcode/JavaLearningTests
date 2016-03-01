package test.learning.java.util;

import static java.util.regex.Pattern.matches;
import static org.junit.Assert.*;

import org.junit.Test;

public class PatternTest {

	@Test
	public void matches_ReturnsTrueIfTheRegexIsExactlyTheSameAsTheInput() {
		assertTrue(matches("", ""));

		assertTrue(matches("a", "a"));
		assertTrue(matches("aa", "aa"));
		
		assertFalse(matches("a", "aa"));
		assertFalse(matches("aa", "a"));
	}
	
	@Test(expected = NullPointerException.class)
	public void mathches_NullValuesThrowsException() {
		matches(null, null);
	}
	
	@Test
	public void matches_CharacterClasses_OneOfTheCharactersSpecified() {
		assertTrue(matches("[ab]", "a"));
		assertTrue(matches("[ab]", "b"));
		
		assertFalse(matches("[ab]", "c"));
		assertFalse(matches("[ab]", "ab"));
		assertFalse(matches("[ab]", ""));
	}
	
	@Test
	public void matches_CharacterClasses_AnyCharacterExceptTheSpecifiedOnes() {
		assertTrue(matches("[^ab]", "c"));
		assertFalse("Should match to single character", matches("[^ab]", "cc"));

		assertFalse(matches("[^ab]", "a"));
		assertFalse(matches("[^ab]", "b"));
		assertFalse(matches("[^ab]", ""));
	}
	
	@Test
	public void matches_CharacterClasses_Range() {
		assertTrue(matches("[a-c]", "a"));
		assertTrue(matches("[a-c]", "b"));
		assertTrue(matches("[a-c]", "c"));
		assertFalse("Out of range", matches("[a-c]", "d"));
		
		assertTrue("a through c, or x through z", matches("[a-cx-z]", "y"));
	}
	
	@Test
	public void matches_CharacterClasses_Union() {
		assertTrue("a through c, or x through z", matches("[a-c[x-z]]", "y"));
	}
	
	@Test
	public void matches_CharacterClasses_Intersection() {
		assertTrue(matches("[a-c&&[bg]]", "b"));
		assertFalse(matches("[a-c&&[bg]]", "c"));
	}
	
	@Test
	public void matches_CharacterClasses_Subtraction() {
		assertTrue(matches("[a-c&&[^bg]]", "c"));
		assertFalse(matches("[a-c&&[^bg]]", "b"));
		
		assertTrue(matches("[a-c&&[^b-d]]", "a"));
		assertFalse(matches("[a-c&&[^b-d]]", "b"));
	}
	
	@Test
	public void matches_PredefinedCharacterClasses_AnyCharacter() {
		assertTrue(matches(".", "a"));
		assertTrue(matches(".", "b"));
		
		assertFalse(matches(".", ""));
		assertFalse(matches(".", "aa"));
	}

	@Test
	public void matches_PredefinedCharacterClasses_Digit() {
		assertTrue(matches("\\d", "0"));
		assertTrue(matches("\\d", "9"));
		
		assertFalse(matches("\\d", "00"));
	}
	
	@Test
	public void matches_PredefinedCharacterClasses_NonDigit() {
		assertTrue(matches("\\D", "z"));
		assertTrue(matches("\\D", ","));
		assertFalse(matches("\\D", "0"));
	}
	
	@Test
	public void matches_PredefinedCharacterClasses_WhiteSpace() {
		assertTrue(matches("\\s", "\t"));
		assertTrue(matches("\\s", "\n"));
		assertTrue(matches("\\s", "\f"));
		assertTrue(matches("\\s", "\r"));
	}
	
	@Test
	public void matches_PredefinedCharacterClasses_NonWhiteSpace() {
		assertTrue(matches("\\S", "a"));
		assertFalse(matches("\\S", "\n"));
	}
	
	@Test
	public void matches_PredefinedCharacterClasses_Word() {
		assertTrue(matches("\\w", "a"));
		assertTrue(matches("\\w", "0"));
		
		assertFalse(matches("\\w", ","));
		assertFalse("Works only for ASCII", matches("\\w", "ç"));
	}
	
	@Test
	public void matches_PredefinedCharacterClasses_NonWord() {
		assertTrue(matches("\\W", ","));
		assertFalse(matches("\\W", "a"));
	}
	
	@Test
	public void matches_GreedyQuantifiers_OnceOrNotAtAll() {
		assertTrue(matches("a?", "a"));
		assertTrue(matches("a?", ""));
		
		assertFalse(matches("a?", "aa"));
	}
	
	@Test
	public void matches_GreedyQuantifiers_ZeroOrMoreTimes() {
		assertTrue(matches("a*", ""));
		assertTrue(matches("a*", "a"));
		assertTrue(matches("a*", "aa"));
		
		assertFalse(matches("a*", "aab"));
	}
	
	@Test
	public void matches_GreedyQuantifiers_OneOrMoreTimes() {
		assertTrue(matches("a+", "a"));
		assertTrue(matches("a+", "aa"));
		
		assertFalse(matches("a+", ""));
		assertFalse(matches("a+", "aab"));
	}
	
	@Test
	public void matches_LogicalOperations_X_FollowedBy_Y() {
		assertTrue(matches("(ab)", "ab"));
		assertTrue(matches("(ab)(ab)", "abab"));
		
		assertFalse(matches("(ab)(ab)", "ababab"));
		
		assertTrue(matches("()", ""));
		assertTrue(matches("((aa))", "aa"));
	}
	
	@Test
	public void matches_LogicalOperations_Either_X_Or_Y() {
		assertTrue(matches("(ab)|(cd)", "ab"));
		assertTrue(matches("(ab)|(cd)", "cd"));
		
		assertFalse(matches("(ab)|(cd)", "ad"));
		assertFalse(matches("(ab)|(cd)", "abcd"));
	}
	
}
