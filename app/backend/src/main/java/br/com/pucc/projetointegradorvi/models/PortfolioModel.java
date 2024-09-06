package br.com.pucc.projetointegradorvi.models;
import java.util.ArrayList;
import java.util.List;

public class PortfolioModel {
    private double accountBalance;
    private List<GraphData> graphData;
    private double buyingPower;
    private double interestAccruedThisMonth;
    private double lifetimeInterestPaid;
    private List<Stock> stocks;

    // Getters e Setters
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<GraphData> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphData> graphData) {
        this.graphData = graphData;
    }

    public double getBuyingPower() {
        return buyingPower;
    }

    public void setBuyingPower(double buyingPower) {
        this.buyingPower = buyingPower;
    }

    public double getInterestAccruedThisMonth() {
        return interestAccruedThisMonth;
    }

    public void setInterestAccruedThisMonth(double interestAccruedThisMonth) {
        this.interestAccruedThisMonth = interestAccruedThisMonth;
    }

    public double getLifetimeInterestPaid() {
        return lifetimeInterestPaid;
    }

    public void setLifetimeInterestPaid(double lifetimeInterestPaid) {
        this.lifetimeInterestPaid = lifetimeInterestPaid;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    // Classe interna para GraphData
    public static class GraphData {
        private String date;
        private double value;

        // Getters e Setters
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    // Classe interna para Stock
    public static class Stock {
        private String ticker;
        private double price;
        private int quantity;

        // Getters e Setters
        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    // Método para retornar um objeto Portfolio mock
    public static PortfolioModel getMockPortfolio() {
        PortfolioModel portfolio = new PortfolioModel();

        // Definir valores mock para o Portfolio
        portfolio.setAccountBalance(5943.00);
        portfolio.setBuyingPower(1500.50);
        portfolio.setInterestAccruedThisMonth(50.00);
        portfolio.setLifetimeInterestPaid(200.00);

        // Mock GraphData
        List<GraphData> graphDataList = new ArrayList<>();
        GraphData graphData1 = new GraphData();
        graphData1.setDate("2024-09-05");
        graphData1.setValue(5000.00);
        GraphData graphData2 = new GraphData();
        graphData2.setDate("2024-09-06");
        graphData2.setValue(5943.00);
        graphDataList.add(graphData1);
        graphDataList.add(graphData2);
        portfolio.setGraphData(graphDataList);

        // Mock Stocks
        List<Stock> stockList = new ArrayList<>();
        Stock stock1 = new Stock();
        stock1.setTicker("AAPL");
        stock1.setPrice(150.00);
        stock1.setQuantity(10);
        Stock stock2 = new Stock();
        stock2.setTicker("GOOGL");
        stock2.setPrice(2800.00);
        stock2.setQuantity(2);
        stockList.add(stock1);
        stockList.add(stock2);
        portfolio.setStocks(stockList);

        return portfolio;
    }
}
