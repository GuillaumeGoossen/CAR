package com.example.TP3.actors;

import akka.actor.UntypedActor;
import akka.actor.Props;

public class SenderActor extends UntypedActor {
    private int totalCount = 0;
    private int responses = 0;

    public static Props props() {
        return Props.create(SenderActor.class, SenderActor::new);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Integer) {
            totalCount += (Integer) message;
            responses++;
            if (responses == 2) {
                getSender().tell(totalCount, getSelf());
                getContext().stop(getSelf());
            }
        } else {
            unhandled(message);
        }
    }
}