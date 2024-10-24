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
    ticker: number;
    buyOrSale: string;
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

export type { UserData, Wallet, FixedIncomeTransaction, VariableIncomeTransaction, CreateWallet , PatchWalletResponse};