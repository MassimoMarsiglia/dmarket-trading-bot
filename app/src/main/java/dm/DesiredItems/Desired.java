package dm.DesiredItems;

import java.util.ArrayList;
import java.util.List;

public class Desired {
    private List<Item> items = new ArrayList<>();

    public Desired(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItemList() {
        return items;
    }

    public void addItems(Item item){
      items.add(item);
    }

    public void removeItems(Item item){
        items.remove(item);
    }

    public String encodedDesiredItems(){
        String namesReformated = "";
        for(Item item:items){
            namesReformated = namesReformated+ "Titles=" + item.getEncodedName() + "&";
        }
        return namesReformated;
    }

    @Override
    public String toString(){
        String desiredString = "";
        for(Item item:items) {
            desiredString = desiredString + item.toString();
        }
        return desiredString;
    }
}
