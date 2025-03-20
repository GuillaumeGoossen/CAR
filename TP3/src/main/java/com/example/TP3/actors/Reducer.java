package com.example.TP3.actors;

import akka.actor.UntypedActor;
import akka.actor.Props;
import com.example.TP3.messages.WordCount;
import com.example.TP3.messages.Word;
import com.example.TP3.messages.WordCountResult;
import java.util.HashMap;
import java.util.Map;

public class Reducer extends UntypedActor {
    private final Map<String, Integer> globalWordCount = new HashMap<>();

    public static Props props() {
        return Props.create(Reducer.class, Reducer::new);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof WordCount) {
            reduce(((WordCount) message).wordCount());
        } else if (message instanceof Word) {
            getWordCount(((Word) message).word());
        } else {
            unhandled(message);
        }
    }

    private void reduce(Map<String, Integer> wordCount) {
        wordCount.forEach((word, count) -> 
            globalWordCount.put(word, globalWordCount.getOrDefault(word, 0) + count)
        );
    }

    private void getWordCount(String word) {
        getSender().tell(new WordCountResult(globalWordCount.getOrDefault(word, 0)), getSelf());
    }
}