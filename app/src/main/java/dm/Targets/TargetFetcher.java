package dm.Targets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import dm.AggregatedPrice.AggregatedPrice;

public class TargetFetcher {

    List<Target> targets = new ArrayList<>();

    private static TargetFetcher instance;

    public static TargetFetcher getInstance() {
        if(instance == null) {
            instance = new TargetFetcher();
        }
        return instance;
    }

    public void removeTargets(int i) {
        targets.remove(i);
    }

    public List<Target> getTargets(){
        return targets;
    }

    public void updateTargetList(String activity, String authToken) throws IOException{
        String url = "https://api.dmarket.com/marketplace-api/v1/user-targets?GameID=a8db&BasicFilters.Status="+activity+"&SortType=UserTargetsSortTypeDefault";
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
        TargetFetcherResponse targetResponse = gson.fromJson(response.toString(), TargetFetcherResponse.class);
        List<Target> targetedItems = targetResponse.getTargetedItems();
    
        for (Target target : targetedItems) {
            boolean found = false;
            for (Target target3 : targets) {
                if (target3.getName().equals(target.getName())) {
                    target3.setPrice(target.getPrice());
                    target3.setTargetID(target.getTargetID());
                    found = true;
                    break;
                }
            }
            // If the item does not exist, add it to the list
            if (!found) {
                targets.add(target);
                target.updateCreationTime();
            }
        }
        //targetedItems.clear();
    }
}
