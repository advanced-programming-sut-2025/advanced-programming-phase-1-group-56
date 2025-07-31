package io.src.model.Network;

import java.util.HashMap;

public class Message {
    private Type type;
    private HashMap<String, Object> body;
    public Message() {}

    public Message(HashMap<String, Object> body, Type type) {
        this.body = body;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        command,
        response,
    }
}
