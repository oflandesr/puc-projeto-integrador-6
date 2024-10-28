import { Ticker } from "./interfaces";

// Utility function to create Basic Auth header
const createBasicAuthHeader = (username: string, password: string) => {
    const token = btoa(`${username}:${password}`);
    return `Basic ${token}`;
};

const formatDate = (stringDate: string) => {
    const date = new Date(stringDate);
    return date.toLocaleDateString();
}

function tickerFilterFunction(ticker: Ticker, criteria: { tickerSearch: string; industrySearch: string; sectorSearch: string }) {
    const matchesTicker = ticker.ticker.toLowerCase().includes(criteria.tickerSearch.toLowerCase());
    const matchesIndustry = criteria.industrySearch === "All" || ticker.industry === criteria.industrySearch;
    const matchesSector = criteria.sectorSearch === "All" || ticker.sector === criteria.sectorSearch;
    return matchesTicker && matchesIndustry && matchesSector;
}

const TickerExtendedName = {
    ticker: 'Ticker',
    currency: 'Currency',
    shortName: 'Short Name',
    longName: 'Long Name',
    address2: 'Address',
    city: 'City',
    state: 'State',
    zip: 'Zip',
    country: 'Country',
    phone: 'Phone',
    website: 'Website',
    industry: 'Industry',
    sector: 'Sector',
    numberOfEmployees: 'Number of Employees',
    regularMarketChange: 'Regular Market Change',
    regularMarketPrice: 'Regular Market Price',
    regularMarketDayHigh: 'Regular Market Day High',
    regularMarketDayLow: 'Regular Market Day Low',
    regularMarketVolume: 'Regular Market Volume',
    regularMarketOpen: 'Regular Market Open',
    priceEarnings: 'Price Earnings',
    earningsPerShare: 'Earnings Per Share'
} as const;


export { createBasicAuthHeader, formatDate, tickerFilterFunction, TickerExtendedName };