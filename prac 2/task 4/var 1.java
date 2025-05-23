import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpClientExample {
    public static void main(String[] args) {
        // 1. URL для GET-запроса (возвращает IP в JSON)
        String url = "https://httpbin.org/ip";

        // 2. Создаем HTTP-клиент
        HttpClient client = HttpClient.newHttpClient();

        // 3. Формируем GET-запрос
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            // 4. Отправляем запрос и получаем ответ
            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            // 5. Проверяем код ответа (200 = OK)
            if (response.statusCode() == 200) {
                // 6. Парсим JSON и извлекаем IP
                String jsonResponse = response.body();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonResponse);
                String originIp = rootNode.get("origin").asText();

                // 7. Выводим IP
                System.out.println("Ваш IP-адрес: " + originIp);
            } else {
                System.out.println("Ошибка запроса. Код: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }
    }
}