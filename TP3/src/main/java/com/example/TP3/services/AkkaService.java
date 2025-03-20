package com.example.TP3.services;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;

import com.example.TP3.actors.Mapper;
import com.example.TP3.actors.Reducer;
import com.example.TP3.messages.Line;
import com.example.TP3.messages.Word;
import org.springframework.stereotype.Service;

import akka.util.Timeout;
import scala.concurrent.Future;

import java.util.List;

@Service
public class AkkaService {
    private final ActorSystem actorSystem = ActorSystem.create("ActorSystem");
    private ActorRef mapper;
    private ActorRef reducer;

    public AkkaService() {
        init();
    }

    private void init() {
        // Initialisation du Reducer
        reducer = actorSystem.actorOf(Reducer.props(), "reducer");
        // Initialisation du Mapper avec une référence au Reducer
        mapper = actorSystem.actorOf(Mapper.props(new ActorRef[]{reducer}), "mapper");
    }

    public void distributeLines(Object input) {
        if (input instanceof String) {
            String line = (String) input;
            mapper.tell(new Line(line), ActorRef.noSender());
        } else if (input instanceof List) {
            List<String> lines = (List<String>) input;
            for (String line : lines) {
                mapper.tell(new Line(line), ActorRef.noSender());
            }
        } else {
            throw new IllegalArgumentException("Input must be a String or List<String>");
        }
    }

    public int getWordCount(String word) {
        System.out.println("Requesting count for word: " + word);
        Timeout timeout = Timeout.apply(scala.concurrent.duration.Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(reducer, word, timeout);
    
        return scala.compat.java8.FutureConverters.toJava(future)
                .thenApply(result -> {
                    System.out.println("Received count for word: " + word + ", count: " + result);
                    return (Integer) result;
                })
                .toCompletableFuture()
                .join();
    }

    public void shutdown() {
        actorSystem.terminate();
    }
}