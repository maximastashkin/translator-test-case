package ru.rsreu.translator.api.services.translating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rsreu.translator.api.services.string_utils.StringIntoWordsAndSignsSplitter;
import ru.rsreu.translator.api.services.string_utils.StringIsWordChecker;
import ru.rsreu.translator.api.translators.Translator;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class WordByWordAsyncTranslationService extends AbstractTranslationService {
    private final ExecutorService executorService;
    private final StringIntoWordsAndSignsSplitter stringSplitter;
    private final StringIsWordChecker stringChecker;

    @Autowired
    public WordByWordAsyncTranslationService(
            Translator translator,
            ExecutorService executorService,
            StringIntoWordsAndSignsSplitter stringSplitter,
            StringIsWordChecker stringChecker) {
        super(translator);
        this.executorService = executorService;
        this.stringSplitter = stringSplitter;
        this.stringChecker = stringChecker;
    }

    @Override
    public String translate(String sourceLanguageCode, String targetLanguageCode, String text) {
        List<String> splitString = stringSplitter.splitIntoWords(text);
        Map<String, Future<String>> translatedWordsFutures = sendAsyncTasksToTranslator(
                formWordsTranslatingTasks(sourceLanguageCode, targetLanguageCode, formSetOfWords(splitString))
        );

        return formTranslatedString(splitString, getTranslatedWordsFromFutures(translatedWordsFutures));
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

    private static void cancelAllTasks(Map<String, Future<String>> translatedWordsFutures) {
        translatedWordsFutures.values().forEach(future -> future.cancel(false));
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

    private Map<String, Future<String>> sendAsyncTasksToTranslator(Map<String, Callable<String>> translatingTasks) {
        return translatingTasks.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, it -> executorService.submit(it.getValue())));
    }

    private Map<String, Callable<String>> formWordsTranslatingTasks(
            String sourceLanguageCode, String targetLanguageCode, Collection<String> words) {
        Map<String, Callable<String>> result = new HashMap<>();
        words.forEach(it -> result.put(it, () -> {
            if (words.size() > 20) {
                Thread.sleep(500);
            }
            return translator.translate(sourceLanguageCode, targetLanguageCode, it);
        }));
        return result;
    }
}