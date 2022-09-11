package ru.rsreu.translator.api.services.translation.word_by_word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsreu.translator.api.services.string_utils.StringIntoWordsAndSignsSplitter;
import ru.rsreu.translator.api.services.string_utils.StringIsWordChecker;
import ru.rsreu.translator.api.services.translation.AsyncTranslatorHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class WordByWordAsyncTranslationService extends WordByWordTranslationService {
    private final AsyncTranslatorHolder asyncTranslatorHolder;
    private final StringIntoWordsAndSignsSplitter stringSplitter;
    private final StringIsWordChecker stringChecker;

    @Autowired
    public WordByWordAsyncTranslationService(
            AsyncTranslatorHolder asyncTranslatorHolder,
            StringIntoWordsAndSignsSplitter stringSplitter,
            StringIsWordChecker stringChecker) {
        this.asyncTranslatorHolder = asyncTranslatorHolder;
        this.stringSplitter = stringSplitter;
        this.stringChecker = stringChecker;
    }

    private static void cancelAllTasks(Map<String, Future<String>> translatedWordsFutures) {
        translatedWordsFutures.values().forEach(future -> future.cancel(false));
    }

    @Override
    public String translate(String sourceLanguageCode, String targetLanguageCode, String text) {
        return getWordByWordTranslation(sourceLanguageCode, targetLanguageCode, text).getTranslatedString();
    }

    @Override
    public WordByWordTranslationResult getWordByWordTranslation(
            String sourceLanguageCode,
            String targetLanguageCode,
            String text
    ) {
        List<String> splitString = stringSplitter.splitIntoWords(text);
        Set<String> uniqueWords = formSetOfWords(splitString);
        Map<String, Future<String>> translatedWordsFutures = new HashMap<>();
        uniqueWords.forEach(it ->
                translatedWordsFutures.put(
                        it, asyncTranslatorHolder.sendTranslationTask(sourceLanguageCode, targetLanguageCode, it)
                )
        );
        Map<String, String> translatedWords = getTranslatedWordsFromFutures(translatedWordsFutures);
        return WordByWordTranslationResult.builder()
                .translatedString(formTranslatedString(splitString, translatedWords))
                .translatedWords(translatedWords).build();
    }

    private Map<String, String> getTranslatedWordsFromFutures(Map<String, Future<String>> translatedWordsFutures) {
        return translatedWordsFutures.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, it -> {
            try {
                return it.getValue().get();
            } catch (InterruptedException e) {
                cancelAllTasks(translatedWordsFutures);
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                cancelAllTasks(translatedWordsFutures);
                throw (RuntimeException) e.getCause();
            }
        }));
    }

    private String formTranslatedString(
            List<String> sourceSplitString,
            Map<String, String> translatedWords
    ) {
        StringBuilder output = new StringBuilder();
        for (String oldPart : sourceSplitString) {
            output.append(getNewResultPart(translatedWords, oldPart));
        }
        return output.toString();
    }

    private String getNewResultPart(Map<String, String> translatedWords, String oldPart) {
        return stringChecker.isWord(oldPart) ? translatedWords.get(oldPart) : oldPart;
    }

    private Set<String> formSetOfWords(List<String> splitString) {
        return splitString.stream().filter(stringChecker::isWord).collect(Collectors.toSet());
    }
}