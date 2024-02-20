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

public class TargetFetcher {

    List<Target> targets = new ArrayList<>();

    private static TargetFetcher instance;

    public static TargetFetcher getInstance() {
        if(instance == null) {
            instance = new TargetFetcher();
        }
        return instance;
    }

    public void updateTargetList() throws IOException{
        String url = "https://api.dmarket.com/marketplace-api/v1/user-targets?GameID=a8db&SortType=UserTargetsSortTypeDefault";
        String acceptHeader = "application/json";
        String authorizationHeader = "eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJlMzI0NDhhMC01NWM3LTQ1YmQtOGRkNi03MjMwNGEzNThmNDIiLCJleHAiOjE3MDg4ODE5NjYsImlhdCI6MTcwNjI4OTk2Niwic2lkIjoiMjhhZDRkNjAtZGI2My00Y2M5LTgwZWUtNzM5ZGFhZGY5NDlhIiwidHlwIjoiYWNjZXNzIiwiaWQiOiIyM2EwYTFhNy1hOWU2LTRiMTItYWE0NC0yMGVkOTMyMmVhYmYiLCJwdmQiOiJtcCIsInBydCI6IjI0MDEiLCJhdHRyaWJ1dGVzIjp7ImFjY291bnRfaWQiOiI3NDkzYTJjMi0wZGZkLTQ3YmMtYTgxZC01ZDBjYjhlZmNhZjYiLCJzYWdhX3dhbGxldF9hZGRyZXNzIjoiMHgzRThmNmZiQzQzMmFGRDI1ZTU0MENGQzdBMjhhRjQ4N2UwYTJEZjA3Iiwid2FsbGV0X2lkIjoiODgxNjI2MGRkMDZiNTlhOGIwNzgwNzYxYTY2MmE4YTIyMTRiMjBkMDlmZDQ4NGU0YTNiZDU3OTNhYmY3NDU5OSJ9fQ.DMPnwZUyW5fwRmVqzC_CSLNgRyuB5D6MURE5Y4WkUFHvXD9fflcfikVT8id-YUv0Mbqjv7Fl3G9JqjbDZeIlsA";

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
    
        // Print response
        System.out.println(targetedItems);
    }
}
