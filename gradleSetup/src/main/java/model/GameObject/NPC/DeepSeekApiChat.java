package model.GameObject.NPC;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class DeepSeekApiChat {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "YOUR_DEEPSEEK_API_KEY";  // ← اینجا کلیدتان را قرار دهید

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();


    public String generateDialogue(String prompt) throws IOException {
        // 1. ساخت JSON درخواست
        ObjectNode body = mapper.createObjectNode();
        body.put("model", "deepseek-chat");
        // ساخت آرایه‌ی messages
        ArrayNode messages = body.putArray("messages");
        messages.addObject()
                .put("role", "system")
                .put("content", "You are a friendly NPC dialogue generator.");
        messages.addObject()
                .put("role", "user")
                .put("content", prompt);
        body.put("stream", false);

        RequestBody reqBody = RequestBody.create(
                body.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // 2. ساخت درخواست HTTP
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(reqBody)
                .build();

        // 3. ارسال و دریافت پاسخ
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            JsonNode root = mapper.readTree(response.body().string());
            String text = root.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText()
                    .trim();
            return text;
        }
    }
}
