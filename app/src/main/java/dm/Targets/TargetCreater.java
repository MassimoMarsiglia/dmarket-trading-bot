package dm.Targets;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TargetCreater {

    String authorizationHeader;

    TargetFetcher targetFetcher;
    
    public TargetCreater(String authToken){
        this.targetFetcher = targetFetcher.getInstance();
        this.authorizationHeader = authToken;

    }
    

    public void createTarget(String itemName, double price) throws IOException, InterruptedException {
        String url = "https://api.dmarket.com/marketplace-api/v1/user-targets/create";
        String token = authorizationHeader;
        
        String name = itemName;

        // JSON payload
        String payload = "{\n" +
                "  \"GameID\": \"a8db\",\n" +
                "  \"Targets\": [\n" +
                "    {\n" +
                "      \"Amount\": \"100\",\n" +
                "      \"Price\": {\n" +
                "        \"Currency\": \"USD\",\n" +
                "        \"Amount\": " + price + "\n" +
                "      },\n" +
                "      \"Title\": \"" + name + "\",\n" +
                "      \"Attrs\": {\n" +
                "\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Build request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        // Send request and retrieve response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print response
        if(response.statusCode()==200){
            targetFetcher.updateTargetList();
        }

        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

}
