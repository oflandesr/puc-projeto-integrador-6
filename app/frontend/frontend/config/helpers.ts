import { AddVariableIncomeTransaction, CustomDropdownV2Options, extraYAxis, Index, Ticker } from "./interfaces";

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
    const matchesTicker = ticker.shortName.toLowerCase().includes(criteria.tickerSearch.toLowerCase());
    const matchesIndustry = criteria.industrySearch === "All" || ticker.industry === criteria.industrySearch;
    const matchesSector = criteria.sectorSearch === "All" || ticker.sector === criteria.sectorSearch;
    return matchesTicker && matchesIndustry && matchesSector;
}

function isAddVariableIncomeTransactionValid(params: AddVariableIncomeTransaction): boolean {
    try {
        return params.date !== "" && params.amount > 0 && params.price > 0;
    } catch (e) {
        console.error(e);
        return false;
    }
}

function convertBuyOrSaleNumberToString(buyOrSale: number) {
    return buyOrSale === 0 ? "Compra" : "Venda";
}

function convertBuyOrSaleStringToNumber(buyOrSale: string) {
    return buyOrSale === "Buy" || "Compra" ? 0 : 1;
}

const TickerExtendedName = {
    currency: "Currency",
    shortName: "Short Name",
    longName: "Long Name",
    address2: "Address",
    city: "City",
    state: "State",
    zip: "Zip",
    country: "Country",
    phone: "Phone",
    website: "Website",
    industry: "Industry",
    sector: "Sector",
    numberOfEmployees: "Number of Employees",
    regularMarketChange: "Regular Market Change",
    regularMarketPrice: "Regular Market Price",
    regularMarketDayHigh: "Regular Market Day High",
    regularMarketDayLow: "Regular Market Day Low",
    regularMarketVolume: "Regular Market Volume",
    regularMarketOpen: "Regular Market Open",
    priceEarnings: "Price Earnings",
    earningsPerShare: "Earnings Per Share",
} as const;

const indexesOptions = ["CDI", "SELIC", "IPCA"];
const typesOptions = ["PRE", "POS", "HIBRIDO"];

const isBuyOrSaleOptions : CustomDropdownV2Options[] = [
    { name : "Buy", value : 0 },
    { name : "Sell", value : 1 }
];


export { isAddVariableIncomeTransactionValid, createBasicAuthHeader, formatDate, tickerFilterFunction, convertBuyOrSaleNumberToString, convertBuyOrSaleStringToNumber, TickerExtendedName, indexesOptions, typesOptions, isBuyOrSaleOptions };