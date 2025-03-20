package com.example.TP3.actors;

import akka.actor.UntypedActor;
import akka.actor.Props;
import com.example.TP3.messages.Word;
import com.example.TP3.messages.WordCount;

import java.util.HashMap;
import java.util.Map;

public class Reducer extends UntypedActor {
    private final Map<String, Integer> wordCounts = new HashMap<>();

    public static Props props() {
        return Props.create(Reducer.class, Reducer::new);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Word) {
            String word = ((Word) message).word();
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        } else if (message instanceof String) {
            getSender().tell(new WordCount(wordCounts), getSelf());
        } else {
            unhandled(message);
        }
    }
}