package dm.Targets;

import dm.AggregatedPrice.*;
import dm.DesiredItems.Desired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TargetUpdater {

    AggregatedPriceFetcher aggregatedPriceFetcher;
    TargetFetcher targetFetcher;
    double minProfit;
    String authorizationHeader;
    Desired desired;

    public TargetUpdater(double profit, String authToken) {
        minProfit = profit;
        authorizationHeader = authToken;
    }

    public void createProfitableTargets() throws IOException {
        aggregatedPriceFetcher.updateAggregatedPrices(authorizationHeader);

        for(AggregatedPrice aggregatedPrice:aggregatedPriceFetcher.getAggregatedPrices()) {
            if(aggregatedPrice.getPercent() >= minProfit) {

            }

        }
    }

    public void deleteUnProfitableTargets() throws IOException, InterruptedException {
        aggregatedPriceFetcher = aggregatedPriceFetcher.getInstance();
        targetFetcher = targetFetcher.getInstance();
        for(AggregatedPrice price : aggregatedPriceFetcher.getAggregatedPrices()) {
            if(price.getPercent() < minProfit){
                //System.out.println(targetFetcher.getTargets().size());
                for(Target targets : targetFetcher.getTargets()){
                    if(price.getMarketHashName().equals(targets.getName())){
                        System.out.println(targets.getName());
                        System.out.println(targets.getTargetID());
                        deleteTargets(targets.getTargetID());
                    }
                }
            }
        }
    }

    public void deleteTargets(String deleteme) throws IOException, InterruptedException {
        String url = "https://api.dmarket.com/marketplace-api/v1/user-targets/delete";
        String token = authorizationHeader;
        
        String targetId = deleteme; // Your variable holding the target ID

        // JSON payload
        String payload = "{\"Targets\": [{\"TargetID\": \"" + targetId + "\"}]}";

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
            for(int i = 0; i < targetFetcher.getTargets().size(); i++){
                if(targetFetcher.getTargets().get(i).getTargetID().equals(deleteme)){
                    targetFetcher.removeTargets(i);
                    break;
                }
            }
        }

        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }
    
}
