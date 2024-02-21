package dm.AggregatedPrice;

import com.google.gson.annotations.SerializedName;

import dm.DesiredItems.*;

public class AggregatedPrice  {

    @SerializedName("MarketHashName")
    private final String marketHashName;

    @SerializedName("Orders")
    private Price orders;

    @SerializedName("Offers")
    private Price offers;

    private double percent;

    public AggregatedPrice(String marketHashName, Price offers, Price orders) {
        this.marketHashName = marketHashName;
        this.offers = offers;
        this.orders = orders;
    }

    public void calculateProfit(Desired desired){

        double fee = 0.9;

        for(Item item : desired.getItemList()) {
            if(item.getName().equals(marketHashName)) {
                fee = item.getFee();
                break;
            }
        }

        double tmp = offers.getBestPriceAsDouble()*fee;

        percent = tmp/orders.getBestPriceAsDouble();
    }

    public double getPercent(){
        return percent;
    }

    //public void updatePercent(){
    //    percent = calculateProfit(offers, orders);
    //}

    public String getMarketHashName() {
        return this.marketHashName;
    }

    public Price getOrder() {
        return this.orders;
    }

    public Price getOffers() {
        return offers;
    }

    public void setOrders(Price order) {
        this.orders = order;
    }

    public void setOffers(Price offer) {
        this.offers = offer;
    }

    //public void calculatePercent(int percent) {
    //    this.percent = percent;
    //}

    @Override
    public String toString() {
        //updatePercent();
        return marketHashName + '\'' +
                ", orders=" + orders +
                ", offers=" + offers +
                ", percent=" + percent;
    }

}
