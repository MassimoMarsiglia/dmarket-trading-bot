package dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import dm.DesiredItems.Item;
import dm.Targets.TargetCreater;
import dm.Targets.TargetFetcher;
import dm.Targets.TargetUpdater;
import dm.AggregatedPrice.AggregatedPriceFetcher;
import dm.DesiredItems.Desired;
import dm.Fees.FeeFetcher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.io.FileInputStream;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        // URL and headers

        String filePath = "C:\\Users\\marsi\\dm\\app\\src\\main\\resources\\config.properties";
        Properties pros;

        pros = new Properties();
        FileInputStream ip = new FileInputStream(filePath);

        pros.load(ip);

        String authToken = pros.getProperty("authToken");
        Double minProfit = Double.parseDouble(pros.getProperty("minProfit"));
        int checkFrequency = Integer.parseInt(pros.getProperty("checkFrequency"));

        String filePathSkins = "app\\src\\main\\resources\\skins.txt";

        List<Item> desiredItems = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePathSkins);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr)) {
            String line;
            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");
                // Parse name and age
                String name = parts[0].trim();
                // Create a Person object and add it to the list
                desiredItems.add(new Item(name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //desiredItems.add(new Item("★ Moto Gloves | Turtle (Battle-Scarred)"));
        //desiredItems.add(new Item("★ Hydra Gloves | Rattler (Field-Tested)"));
        //desiredItems.add(new Item("★ Ursus Knife | Safari Mesh (Field-Tested)"));
        //desiredItems.add(new Item("★ Nomad Knife | Scorched (Field-Tested)"));
        //desiredItems.add(new Item("★ Shadow Daggers | Black Laminate (Minimal Wear)"));
        Desired desired = new Desired(desiredItems);

        TargetFetcher targetFetcher = TargetFetcher.getInstance();

        targetFetcher.updateTargetList("TargetStatusActive", authToken);

        TargetCreater targetCreater = new TargetCreater(authToken);
        
        TargetUpdater targetUpdater = new TargetUpdater(minProfit, authToken, targetCreater);

        FeeFetcher feeFetcher = FeeFetcher.getInstance();
        feeFetcher.getFees(desired, authToken);

        System.out.println(desired.getItemList());

        AggregatedPriceFetcher aggregatedPriceFetcher = AggregatedPriceFetcher.getInstance();
        aggregatedPriceFetcher.setDesired(desired);

        int counter = 0;
        
        while(true){
            System.out.println(counter);
            
            aggregatedPriceFetcher.updateProfitPercent(authToken);
        
            targetUpdater.deleteUnProfitableTargets();
            counter++;
            targetUpdater.createProfitableTargets();

            if (counter == 60) {
                targetUpdater.deleteAllTargets();
                counter = 0;
                synchronized (App.class) {
                    App.class.wait(180000);
                }
            }
            synchronized (App.class) {
                App.class.wait(checkFrequency);
            }
        }
        

        //System.out.println(desired.toString());



        //FeeFetcher feeFetcher = FeeFetcher.getInstance();
        //feeFetcher.updateFees();

        //TargetFetcherLoop.targetFetcherLoop(targetFetcher);
    }
}
