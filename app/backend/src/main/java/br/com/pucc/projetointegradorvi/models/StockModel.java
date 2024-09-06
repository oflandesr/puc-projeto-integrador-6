package br.com.pucc.projetointegradorvi.models;

public class StockModel {
    private String ticker;
    private String name;
    private double price;

    // Getters e Setters
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Método para retornar um objeto StockModel mock
    public static StockModel getMockStockModel() {
        StockModel stockModel = new StockModel();
        stockModel.setTicker("AAPL");
        stockModel.setName("Apple Inc.");
        stockModel.setPrice(150.00);
        return stockModel;
    }
}
