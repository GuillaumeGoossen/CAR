package com.example.TP3.actors;

import akka.actor.UntypedActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import com.example.TP3.messages.Line;
import com.example.TP3.messages.Word;

public class Mapper extends UntypedActor {
    private final ActorRef[] reducers;

    public Mapper(ActorRef[] reducers) {
        this.reducers = reducers;
    }

    public static Props props(ActorRef[] reducers) {
        return Props.create(Mapper.class, () -> new Mapper(reducers));
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Line) {
            processLine(((Line) message).line());
        } else {
            unhandled(message);
        }
    }

    private void processLine(String line) {
        String[] words = line.split("\\s+");
        for (String word : words) {
            ActorRef reducer = partition(reducers, word);
            reducer.tell(new Word(word), getSelf());
        }
    }

    private ActorRef partition(ActorRef[] reducers, String word) {
        int index = Math.abs(word.hashCode()) % reducers.length;
        return reducers[index];
    }
}