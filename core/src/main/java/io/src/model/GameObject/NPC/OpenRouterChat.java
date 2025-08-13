package io.src.model.GameObject.NPC;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class OpenRouterChat {
    // OpenRouter chat completions endpoint (OpenAI-compatible)
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    // ست کن متغیر محیطی قبل از اجرای برنامه: OPENROUTER_API_KEY
    private static final String API_KEY = System.getenv("OPENROUTER_API_KEY");

    private final OkHttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenRouterChat() {
        // timeout ها قابل تنظیمند
        this.client = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(60))
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(60))
            .build();
    }

    /**
     * Generate a chat response from the model.
     * @param model the model id, e.g. "qwen/qwq-32b:free" or "qwen/qwen3-30b-a3b:free"
     * @param userPrompt user's prompt text
     * @return assistant reply (String)
     */
    public String generateDialogue(String model, String userPrompt) throws IOException {
        if (API_KEY == null || API_KEY.isBlank()) {
            throw new IllegalStateException("OPENROUTER_API_KEY environment variable not set");
        }

        // Build JSON body following OpenAI-like chat schema
        ObjectNode root = mapper.createObjectNode();
        root.put("model", model);
        // messages: system/user/assistant roles
        root.putArray("messages").add(mapper.createObjectNode()
            .put("role", "user")
            .put("content", userPrompt)
        );

        // optional params
        root.put("temperature", 0.7);
        root.put("max_tokens", 1024);

        RequestBody body = RequestBody.create(
            root.toString(),
            MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer " + API_KEY)
            // Optional: identify your app (OpenRouter supports extra headers but not required)
            //.addHeader("OpenRouter-Organization", "your-app-name")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String respBody = response.body() != null ? response.body().string() : "";
                throw new IOException("OpenRouter API error: " + response.code() + " - " + respBody);
            }
            String respText = response.body() != null ? response.body().string() : "{}";
            JsonNode rootNode = mapper.readTree(respText);

            // OpenRouter tries to be OpenAI-compatible: try common paths
            // 1) choices[0].message.content  (chat completions)
            if (rootNode.has("choices") && rootNode.get("choices").isArray() && rootNode.get("choices").size() > 0) {
                JsonNode firstChoice = rootNode.get("choices").get(0);
                if (firstChoice.has("message") && firstChoice.get("message").has("content")) {
                    return firstChoice.get("message").get("content").asText().trim();
                }
                // sometimes choices[].text (completions style)
                if (firstChoice.has("text")) {
                    return firstChoice.get("text").asText().trim();
                }
                // sometimes choices[].message.content may be nested differently
                if (firstChoice.has("delta") && firstChoice.get("delta").has("content")) {
                    return firstChoice.get("delta").get("content").asText().trim();
                }
            }

            // Fallback: if OpenRouter returns 'output' or provider-specific fields
            if (rootNode.has("output") && rootNode.get("output").isArray() && rootNode.get("output").size() > 0) {
                return rootNode.get("output").get(0).asText().trim();
            }

            // As last resort, return whole response as string for debugging
            return rootNode.toString();
        }
    }

    // quick usage example
    public static void main(String[] args) throws Exception {
        OpenRouterChat client = new OpenRouterChat();
        String model = "qwen/qwq-32b:free"; // یا مدل دلخواه خودت
        String prompt = "Write a short friendly greeting for a game NPC that sells animals.";
        String reply = client.generateDialogue(model, prompt);
        System.out.println("MODEL REPLIED: " + reply);
    }
}
