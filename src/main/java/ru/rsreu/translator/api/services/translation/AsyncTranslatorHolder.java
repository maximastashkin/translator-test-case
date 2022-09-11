package ru.rsreu.translator.api.services.translation;

import ru.rsreu.translator.translators.Translator;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AsyncTranslatorHolder {
    private final Translator translator;
    private final ExecutorService executorService;
    private final int hitsPerSecond;
    private final int threadsCount;

    private long hitsDelayMs = 0;

    public AsyncTranslatorHolder(
            Translator translator,
            ExecutorService executorService,
            int hitsPerSecond,
            int threadsCount) {
        this.translator = translator;
        this.executorService = executorService;
        this.hitsPerSecond = hitsPerSecond;
        this.threadsCount = threadsCount;
    }

    @PostConstruct
    public void init() {
        if (hitsPerSecond != 0) {
            hitsDelayMs = (long) (((double) threadsCount / hitsPerSecond) * 1000L) + 15;
        }
    }

    public Future<String> sendTranslationTask(String sourceLanguageCode, String targetLanguageCode, String text) {
        return executorService.submit(() -> {
            try {
                Thread.sleep(hitsDelayMs);
            } catch (InterruptedException e) {
                throw new InterruptedException();
            }
            return translator.translate(sourceLanguageCode, targetLanguageCode, text);
        });
    }
}
