package dm.Fees;

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

public class FeeFetcher {

    private List<Fee> fees = new ArrayList<>();

    private static FeeFetcher instance;

    public static FeeFetcher getInstance() {
        if(instance == null) {
            instance = new FeeFetcher();
        }
        return instance;
    }

    public void getFees(Desired desire) throws IOException{
        updateFees();

        for(Item items: desire.getItemList()) {
            for(Fee fee : fees) {
                if(items.getName().equals(fee.getName())){
                    items.setFee(fee.feeAsDouble());
                }

            }
        }
    }
    
    private void updateFees() throws IOException{
        String url = "https://api.dmarket.com/exchange/v1/customized-fees?gameId=a8db&offerType=dmarket&limit=10000&offset=0";
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

        //System.out.println(response);

        Gson gson = new Gson();
        FeeResponse feeResponse = gson.fromJson(response.toString(), FeeResponse.class);
        List<Fee> feeList = feeResponse.getFees();
    
        // Print response
        //System.out.println(feeList);

        fees = feeList;

    }
}
