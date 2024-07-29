public class ObserverPatternTest {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();

        MobileApp mobileApp = new MobileApp();
        WebApp webApp = new WebApp();

        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);

        stockMarket.setStockPrice(162.29);
        stockMarket.setStockPrice(198.20);

        stockMarket.deregisterObserver(webApp);

        stockMarket.setStockPrice(185.89);

        stockMarket.registerObserver(webApp);

        stockMarket.setStockPrice(199.99);
    }
}