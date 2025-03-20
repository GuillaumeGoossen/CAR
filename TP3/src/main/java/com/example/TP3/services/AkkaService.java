package com.example.TP3.services;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.example.TP3.actors.Mapper;
import com.example.TP3.actors.Reducer;
import com.example.TP3.messages.Line;
import com.example.TP3.messages.Word;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AkkaService {
    private final ActorSystem actorSystem = ActorSystem.create("MapReduceSystem");
    private ActorRef[] mappers = new ActorRef[3];
    private ActorRef[] reducers = new ActorRef[2];

    public AkkaService() {
        init();
    }

    private void init() {
        for (int i = 0; i < reducers.length; i++) {
            reducers[i] = actorSystem.actorOf(Reducer.props(), "reducer" + i);
        }
        for (int i = 0; i < mappers.length; i++) {
            mappers[i] = actorSystem.actorOf(Mapper.props(reducers), "mapper" + i);
        }
    }

    public void distributeLines(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            mappers[i % mappers.length].tell(new Line(lines.get(i)), ActorRef.noSender());
        }
    }

    public void getWordCount(String word, ActorRef sender) {
        for (ActorRef reducer : reducers) {
            reducer.tell(new Word(word), sender);
        }
    }
}