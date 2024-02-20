package dm.AggregatedPrice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import dm.DesiredItems.*;

public class AggregatedPriceFetcher{

private List<AggregatedPrice> aggregatedPrices = new ArrayList<>();

private Desired desired;

private static AggregatedPriceFetcher instance;

private AggregatedPriceFetcher() {}

    public static AggregatedPriceFetcher getInstance() {
        if(instance == null) {
            instance = new AggregatedPriceFetcher();
        }
        return instance;
    }

    public void setDesired(Desired desired) {
        this.desired = desired;
    }

    public Desired getDesired(){
        return this.desired;
    }

    public List<AggregatedPrice> getAggregatedPrices() {
        return aggregatedPrices;
    }

    public void setAggregatedPrices(List<AggregatedPrice> AggregatedPrices) {
        this.aggregatedPrices = AggregatedPrices;
    }

    public void addAggregatedPrice(AggregatedPrice aggregatedPrice) {
        this.aggregatedPrices.add(aggregatedPrice);
    }

    public void updateAggregatedPrices(String authToken) throws IOException{
        String url = "https://api.dmarket.com/price-aggregator/v1/aggregated-prices?" + desired.encodedDesiredItems();
        String acceptHeader = "application/json";
        String authorizationHeader = authToken;

        // Create connection
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set request method
        con.setRequestMethod("GET");

        // Set request headers
        con.setRequestProperty("accept", acceptHeader);
        con.setRequestProperty("Authorization", authorizationHeader);

        // Get response code
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Gson gson = new Gson();
        AggregatedPriceResponse priceResponse = gson.fromJson(response.toString(), AggregatedPriceResponse.class);
        //aggregatedPrices = priceResponse.getAggregatedTitles();

        for (AggregatedPrice newAggregatedPrice : priceResponse.getAggregatedTitles()) {
            boolean found = false;
            for (AggregatedPrice aggregatedPrice : aggregatedPrices) {
                if (aggregatedPrice.getMarketHashName().equals(newAggregatedPrice.getMarketHashName())) {
                    aggregatedPrice.setOrders(newAggregatedPrice.getOrder());
                    aggregatedPrice.setOffers(newAggregatedPrice.getOffers());
                    aggregatedPrice.calculateProfit(desired);
                    found = true;
                    break;
                }
            }
            // If the item does not exist, add it to the list
            if (!found) {
                aggregatedPrices.add(newAggregatedPrice);
            }
        }
    }

    public void updateProfitPercent(String authorizationHeader) throws IOException {
        updateAggregatedPrices(authorizationHeader);
        for(AggregatedPrice price : aggregatedPrices) {
            price.calculateProfit(desired);
        }
    }

}