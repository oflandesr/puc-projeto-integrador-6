import { AddVariableIncomeTransaction, CustomDropdownV2Options, extraYAxis, FilterWalletDistribution, Index, Ticker, Wallet } from "./interfaces";

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
    return buyOrSale === 1 ? "Compra" : "Venda";
}

function convertBuyOrSaleStringToNumber(buyOrSale: string) {
    return buyOrSale === "Buy" || "Compra" ? 1 : 0;
}

const TickerExtendedName = {
    currency: "Moeda",
    shortName: "Nome Curto",
    longName: "Nome Longo",
    address2: "Endereço",
    city: "Cidade",
    state: "Estado",
    zip: "CEP",
    country: "País",
    phone: "Telefone",
    website: "Site",
    industry: "Indústria",
    sector: "Setor",
    numberOfEmployees: "Número de Funcionários",
    regularMarketChange: "Mudança no Mercado Regular",
    regularMarketPrice: "Preço no Mercado Regular",
    regularMarketDayHigh: "Máximo do Dia no Mercado Regular",
    regularMarketDayLow: "Mínimo do Dia no Mercado Regular",
    regularMarketVolume: "Volume no Mercado Regular",
    regularMarketOpen: "Abertura do Mercado Regular",
    priceEarnings: "Preço/Lucro",
    earningsPerShare: "Lucro por Ação",
} as const;

const indexesOptions = ["CDI", "SELIC", "IPCA"];
const typesOptions = ["PRE", "POS", "HIBRIDO"];

const isBuyOrSaleOptions : CustomDropdownV2Options[] = [
    { name : "Buy", value : 1 },
    { name : "Sell", value : 0 }
];

const filterWalletDistribution = (walletData: Wallet) : FilterWalletDistribution => {
    let totalFixed, totalStock, totalFII;

    totalFixed = walletData.fixedIncomeTransactions.map((transaction) => transaction.value).reduce((acc, cur) => acc + cur, 0);

    // stocks do not have the id ending with 11
    totalStock = walletData.variableIncomeTransactions.map((transaction) => {
        if (!transaction.ticker.id.endsWith("11")) {
            return transaction.amount * transaction.price;
        }
        return 0;
    }).reduce((acc, cur) => acc + cur, 0);

    totalFII = walletData.variableIncomeTransactions.map((transaction) => {
        if (transaction.ticker.id.endsWith("11")) {
            return transaction.amount * transaction.price;
        }
        return 0;
    }).reduce((acc, cur) => acc + cur, 0);

    // now we need to make it out of 100%, the sum should be 100
    const total = totalFixed + totalStock + totalFII;
    totalFixed = (totalFixed / total) * 100;
    totalStock = (totalStock / total) * 100;
    totalFII = (totalFII / total) * 100;

    if (isNaN(totalFixed)) totalFixed = 0;
    if (isNaN(totalStock)) totalStock = 0;
    if (isNaN(totalFII)) totalFII = 0;

    // make all of them integers
    totalFixed = Math.round(totalFixed);
    totalStock = Math.round(totalStock);
    totalFII = Math.round(totalFII);

    // if the sum is not 100, we need to adjust it
    const sum = totalFixed + totalStock + totalFII;
    if (sum !== 100) {
        const diff = 100 - sum;
        if (diff > 0) {
            if (totalFixed > 0) {
                totalFixed += diff;
            } else if (totalStock > 0) {
                totalStock += diff;
            } else if (totalFII > 0) {
                totalFII += diff;
            }
        } else {
            if (totalFixed > 0) {
                totalFixed -= diff;
            } else if (totalStock > 0) {
                totalStock -= diff;
            } else if (totalFII > 0) {
                totalFII -= diff;
            }
        }
    }

    return { 
        totalFixed: totalFixed.toString(), 
        totalStock: totalStock.toString(), 
        totalFII: totalFII.toString() 
    };
}

export { filterWalletDistribution, isAddVariableIncomeTransactionValid, createBasicAuthHeader, formatDate, tickerFilterFunction, convertBuyOrSaleNumberToString, convertBuyOrSaleStringToNumber, TickerExtendedName, indexesOptions, typesOptions, isBuyOrSaleOptions };