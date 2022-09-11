package ru.rsreu.translator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.rsreu.translator.api.services.string_utils.StringIntoWordsAndSignsSplitter;

import java.util.Arrays;

public class StringSplitterTest {
    private final StringIntoWordsAndSignsSplitter splitter = new StringIntoWordsAndSignsSplitter();

    @Test
    public void splitWithoutSignsTest() {
        Assertions.assertEquals(
                Arrays.asList("This", " ", "is", " ", "test", " ", "string", " ", "without", " ", "punctuation"),
                splitter.splitIntoWords("This is test string without punctuation"));
    }

    @Test
    public void splitWithSignOnEndingTest() {
        Assertions.assertEquals(
                Arrays.asList("This", " ", "is", " ", "test", " ", "string",
                        " ", "with", " ", "point", " ", "in", " ", "the", " ", "end", "."),
                splitter.splitIntoWords("This is test string with point in the end."));
    }

    @Test
    public void splitWithSignsTest() {
        Assertions.assertEquals(
                Arrays.asList("This", " ", "is", ", ", "test", " ", "string", ",", "with", " ", "signs", "! ", "Yes", "."),
                splitter.splitIntoWords("This is, test string,with signs! Yes."));
    }
}
