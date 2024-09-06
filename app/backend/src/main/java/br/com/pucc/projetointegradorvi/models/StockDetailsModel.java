package br.com.pucc.projetointegradorvi.models;

import java.util.ArrayList;
import java.util.List;

public class StockDetailsModel {
    private String ticker;
    private double price;
    private String change;
    private List<GraphData> graphData;
    private About about;
    private Stats stats;
    private List<News> news;

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

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public List<GraphData> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphData> graphData) {
        this.graphData = graphData;
    }

    public About getAbout() {
        return about;
    }

    public void setAbout(About about) {
        this.about = about;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
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

    // Classe interna para About
    public static class About {
        private String description;
        private String CEO;
        private String founded;
        private String employees;
        private String headquarters;

        // Getters e Setters
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCEO() {
            return CEO;
        }

        public void setCEO(String CEO) {
            this.CEO = CEO;
        }

        public String getFounded() {
            return founded;
        }

        public void setFounded(String founded) {
            this.founded = founded;
        }

        public String getEmployees() {
            return employees;
        }

        public void setEmployees(String employees) {
            this.employees = employees;
        }

        public String getHeadquarters() {
            return headquarters;
        }

        public void setHeadquarters(String headquarters) {
            this.headquarters = headquarters;
        }
    }

    // Classe interna para Stats
    public static class Stats {
        private double open;
        private double high;
        private double low;
        private String marketCap;
        private String volume;
        private String dividendYield;

        // Getters e Setters
        public double getOpen() {
            return open;
        }

        public void setOpen(double open) {
            this.open = open;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public double getLow() {
            return low;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public String getMarketCap() {
            return marketCap;
        }

        public void setMarketCap(String marketCap) {
            this.marketCap = marketCap;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getDividendYield() {
            return dividendYield;
        }

        public void setDividendYield(String dividendYield) {
            this.dividendYield = dividendYield;
        }
    }

    // Classe interna para News
    public static class News {
        private String title;
        private String source;
        private String url;

        // Getters e Setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    // Método para retornar um objeto StockDetailsModel mock
    public static StockDetailsModel getMockStockDetailsModel() {
        StockDetailsModel stockDetailsModel = new StockDetailsModel();
        stockDetailsModel.setTicker("AMZN");
        stockDetailsModel.setPrice(109.43);
        stockDetailsModel.setChange("+2%");

        // Mock GraphData
        List<GraphData> graphDataList = new ArrayList<>();
        GraphData graphData1 = new GraphData();
        graphData1.setDate("2024-09-05");
        graphData1.setValue(108.00);
        graphDataList.add(graphData1);
        stockDetailsModel.setGraphData(graphDataList);

        // Mock About
        About about = new About();
        about.setDescription("Amazon is a multinational technology company...");
        about.setCEO("Andrew Jassy");
        about.setFounded("1994");
        about.setEmployees("1.3M");
        about.setHeadquarters("Seattle, Washington");
        stockDetailsModel.setAbout(about);

        // Mock Stats
        Stats stats = new Stats();
        stats.setOpen(108.15);
        stats.setHigh(109.50);
        stats.setLow(107.80);
        stats.setMarketCap("1.7T");
        stats.setVolume("75M");
        stats.setDividendYield("0.80%");
        stockDetailsModel.setStats(stats);

        // Mock News
        List<News> newsList = new ArrayList<>();
        News news1 = new News();
        news1.setTitle("Amazon tops tech stocks for 2024");
        news1.setSource("MarketWatch");
        news1.setUrl("https://marketwatch.com/amazon-news");
        newsList.add(news1);
        stockDetailsModel.setNews(newsList);

        return stockDetailsModel;
    }
}

