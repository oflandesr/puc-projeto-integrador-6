interface UserData {
    id?: number;
    roles?: {
        role: string;
        description: string;
    }[];
    login?: string;
    firstName?: string;
    lastName?: string;
}

interface FixedIncomeTransaction {
    id: number;
    institution: string;
    type: string;
    value: number;
    startDate: string;
    endDate: string;
    indexName: string;
    taxValue: number;
}

interface VariableIncomeTransaction {
    id: number;
    ticker: Ticker;
    buyOrSale: number;
    date: string;
    amount: number;
    price: number;
}

interface CreateWallet {
    user: number;
    name: string;
    objective: string;
    intenFixIncPercent: number;
    intenStockPercent: number;
    intenFilPercent: number;
}

interface PatchWalletResponse {
    name: string;
    objective: string;
    intenFixIncPercent: string;
    intenStockPercent: string;
    intenFilPercent: string;
}

interface Wallet {
    name: string;
    objective: string;
    intenFixIncPercent: string;
    intenStockPercent: string;
    intenFilPercent: string;
    id: number;
    user: number;
    fixedIncomeTransactions: FixedIncomeTransaction[];
    variableIncomeTransactions: VariableIncomeTransaction[];
}

interface Ticker {
    ticker: string;
    currency: string;
    shortName: string;
    longName: string;
    address2: string;
    city: string;
    state: string;
    zip: string;
    country: string;
    phone: string;
    website: string;
    industry: string;
    sector: string;
    numberOfEmployees: number;
    regularMarketChange: number;
    regularMarketPrice: number;
    regularMarketDayHigh: number;
    regularMarketDayLow: number;
    regularMarketVolume: number;
    regularMarketOpen: number;
    priceEarnings: number;
    earningsPerShare: number;
}

interface AddFixedIncomeTransaction {
    institution: string;
    type: string;
    value: number;
    startDate: string;
    endDate: string;
    indexName: string;
    taxValue: number;
}

interface AddFixedIncomeTransactionResponse {
    id: number;
    institution: string;
    type: string;
    value: number;
    startDate: string;
    endDate: string;
    indexName: string;
    taxValue: number;
}

interface AddFixedIncomeTransaction {
    institution: string;
    type: string;
    value: number;
    startDate: string;
    endDate: string;
    indexName: string;
    taxValue: number;
}

interface AddVariableIncomeTransaction {
    ticker: string;
    buyOrSale: number;
    date: string;
    amount: number;
    price: number;
}

export type { 
    UserData, 
    Wallet, 
    FixedIncomeTransaction, 
    VariableIncomeTransaction, 
    CreateWallet, 
    PatchWalletResponse, 
    Ticker ,
    AddFixedIncomeTransaction,
    AddFixedIncomeTransactionResponse,
    AddVariableIncomeTransaction
};