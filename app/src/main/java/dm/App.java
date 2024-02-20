package dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import dm.DesiredItems.Item;

import dm.Targets.TargetFetcher;
import dm.AggregatedPrice.AggregatedPriceFetcher;
import dm.DesiredItems.Desired;
import dm.Fees.FeeFetcher;

import java.io.IOException;
import java.io.FileInputStream;

public class App {
    public static void main(String[] args) throws IOException {
        // URL and headers

        String filePath = "C:\\Users\\marsi\\dm\\app\\src\\main\\resources\\config.properties";
        Properties pros;

        pros = new Properties();
        FileInputStream ip = new FileInputStream(filePath);

        pros.load(ip);

        String authToken = pros.getProperty("authToken");
        Double minProfit = Double.parseDouble(pros.getProperty("minProfit"));
        int checkFrequency = Integer.parseInt(pros.getProperty("checkFrequency"));


        List<Item> desiredItems = new ArrayList<>();

        desiredItems.add(new Item("Sticker Capsule 2", 2));
        desiredItems.add(new Item("★ Falchion Knife | Freehand (Minimal Wear)", 2));
        desiredItems.add(new Item("★ Ursus Knife | Safari Mesh (Field-Tested)", 1));
        desiredItems.add(new Item("★ Nomad Knife | Scorched (Field-Tested)", 3));
        desiredItems.add(new Item("★ Shadow Daggers | Black Laminate (Minimal Wear)", 1));
        Desired desired = new Desired(desiredItems);

        FeeFetcher feeFetcher = FeeFetcher.getInstance();
        feeFetcher.getFees(desired);

        AggregatedPriceFetcher aggregatedPriceFetcher = AggregatedPriceFetcher.getInstance();
        aggregatedPriceFetcher.setDesired(desired);
        aggregatedPriceFetcher.updateAggregatedPrices(authToken);
        aggregatedPriceFetcher.updateProfitPercent(authToken);
        System.out.println(aggregatedPriceFetcher.getAggregatedPrices());
        
        //System.out.println(desired.toString());

        //TargetFetcher targetFetcher = TargetFetcher.getInstance();
        //targetFetcher.updateTargetList();

        //FeeFetcher feeFetcher = FeeFetcher.getInstance();
        //feeFetcher.updateFees();

        //TargetFetcherLoop.targetFetcherLoop(targetFetcher);
    }
}
