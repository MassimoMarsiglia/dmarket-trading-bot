package dm.Targets;

import dm.AggregatedPrice.*;
import dm.DesiredItems.Desired;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;

public class TargetUpdater {

    AggregatedPriceFetcher aggregatedPriceFetcher;
    TargetFetcher targetFetcher;
    double minProfit;
    String authorizationHeader;
    Desired desired;
    TargetCreater targetCreater;

    public TargetUpdater(double profit, String authToken, TargetCreater target) {
        minProfit = profit;
        authorizationHeader = authToken;
        targetCreater = target;
    }

    public void createProfitableTargets() throws IOException, InterruptedException {
        aggregatedPriceFetcher.updateProfitPercent(authorizationHeader);

        for(AggregatedPrice aggregatedPrice:aggregatedPriceFetcher.getAggregatedPrices()) {
            boolean found = false;
            if(aggregatedPrice.getPercent() >= minProfit) {
                for(Target targeted : targetFetcher.getTargets()){
                    if(targeted.getName().equals(aggregatedPrice.getMarketHashName())){
                        found = true;
                        if(targeted.getPrice().getPriceAsDouble() < aggregatedPrice.getOrder().getBestPriceAsDouble()){ 
                            if(System.currentTimeMillis()/1000 > targeted.getCreationTime()+900){    //+900 because of 15mins
                            deleteTargets(targeted.getTargetID());
                            targetCreater.createTarget(aggregatedPrice.getMarketHashName(), aggregatedPrice.getOrder().getBestPriceAsDouble()+0.01);
                            break;
                            }
                        }
                    }
                }
                if(!found) {
                    targetCreater.createTarget(aggregatedPrice.getMarketHashName(), aggregatedPrice.getOrder().getBestPriceAsDouble()+0.01);
                }
            }
        }
    }

    public void deleteUnProfitableTargets() throws IOException, InterruptedException {
        aggregatedPriceFetcher = aggregatedPriceFetcher.getInstance();
        targetFetcher = targetFetcher.getInstance();

        List<Target> targetsToDelete = new ArrayList<>();

        for (AggregatedPrice price : aggregatedPriceFetcher.getAggregatedPrices()) {
            if (price.getPercent() < minProfit) {
                for (Target target : targetFetcher.getTargets()) {
                    if (price.getMarketHashName().equals(target.getName())) {
                        // Add the target to the list of targets to delete
                        targetsToDelete.add(target);
                    }
                }
            }
        }
        
        // Delete the targets after the iteration is complete
        for (Target target : targetsToDelete) {
            System.out.println(target.getName());
            System.out.println(target.getTargetID());
            deleteTargets(target.getTargetID());
        }
    }

    public void deleteAllTargets() throws IOException, InterruptedException{
        try {
            targetFetcher.updateTargetList("TargetStatusActive", authorizationHeader);
            targetFetcher.updateTargetList("TargetStatusInactive", authorizationHeader);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
        List<Target> targetsCopy = new ArrayList<>(targetFetcher.getTargets());

        // Iterate over the copy
        for(Target target1: targetsCopy) {
            deleteTargets(target1.getTargetID());
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
