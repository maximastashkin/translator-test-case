package ru.rsreu.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rsreu.translator.api.services.string_utils.StringIsWordChecker;

public class StringIsWordCheckerTest {
    private final StringIsWordChecker stringIsWordChecker = new StringIsWordChecker();

    @Test
    public void testWordWithSign() {
        Assertions.assertFalse(stringIsWordChecker.isWord("Test!"));
    }

    @Test
    public void testWord() {
        Assertions.assertTrue(stringIsWordChecker.isWord("Test"));
    }

    @Test
    public void testManySpaces() {
        Assertions.assertFalse(stringIsWordChecker.isWord("    "));
    }

    @Test
    public void testSpace() {
        Assertions.assertFalse(stringIsWordChecker.isWord(" "));
    }

    @Test
    public void testSign() {
        Assertions.assertFalse(stringIsWordChecker.isWord(","));
    }

    @Test
    public void testSignWithSpace() {
        Assertions.assertFalse(stringIsWordChecker.isWord(", "));
    }
}
