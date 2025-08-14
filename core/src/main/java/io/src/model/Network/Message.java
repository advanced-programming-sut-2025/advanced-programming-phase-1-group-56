package io.src.model.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.src.model.TimeSystem.LocalDateTimeAdapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
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
    public <T> T getFromBody1(String fieldName, Class<T> clazz) {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        Object value = body.get(fieldName);
        return gson.fromJson(gson.toJson(value), clazz);
    }


    public <T> T getFromBody2(String fieldName, java.lang.reflect.Type type) {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        Object value = body.get(fieldName);
        return gson.fromJson(gson.toJson(value), type);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public enum Type {
        command,
        response,
    }
}
