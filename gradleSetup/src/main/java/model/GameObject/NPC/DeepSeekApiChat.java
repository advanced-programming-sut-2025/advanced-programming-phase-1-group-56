package model.GameObject.NPC;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class DeepSeekApiChat {
    private static final String API_URL = "https://api-inference.huggingface.co/models/tiiuae/falcon-7b-instruct";
    private static final String API_KEY =  "";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String generateDialogue(String prompt) throws IOException {
        ObjectNode body = mapper.createObjectNode();
        body.put("inputs", prompt);
        RequestBody reqBody = RequestBody.create(
                body.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(reqBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("خطا از سمت Hugging Face: " + response.code() + " " + response.message());
            }
            JsonNode root = mapper.readTree(response.body().string());
            return root.get(0).get("generated_text").asText().trim();
        }
    }
}
