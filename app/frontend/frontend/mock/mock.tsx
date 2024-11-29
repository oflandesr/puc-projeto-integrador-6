import Link from "next/link";
import { TableColumn } from "react-data-table-component";
import { FixedIncomeTransaction, FixedInvestmentInterest, VariableIncomeTransaction, Wallet } from "@/config/interfaces";
import { convertBuyOrSaleNumberToString, formatDate } from "@/config/helpers";
import CustomIconButton from "@/components/Button/CustomIconButton";
import { FaRegTrashAlt } from "react-icons/fa";

interface InterfaceUserTableData {
    id: number;
    name: string;
    objective: string;
    intenFixIncPercent: string;
    intenStockPercent: string;
    intenFilPercent: string;
}

const tableData: InterfaceUserTableData[] = [
    { id: 1, name: "John Doe", objective: "Objective 1", intenFixIncPercent: "10", intenStockPercent: "20", intenFilPercent: "70" },
    { id: 2, name: "Jane Johnson", objective: "Objective 2", intenFixIncPercent: "20", intenStockPercent: "30", intenFilPercent: "50" },
    { id: 3, name: "John Smith", objective: "Objective 3", intenFixIncPercent: "30", intenStockPercent: "40", intenFilPercent: "30" },
    { id: 4, name: "Jane Smith", objective: "Objective 4", intenFixIncPercent: "40", intenStockPercent: "50", intenFilPercent: "10" },
    { id: 5, name: "John Johnson", objective: "Objective 5", intenFixIncPercent: "50", intenStockPercent: "30", intenFilPercent: "20" },
    { id: 6, name: "Jane Doe", objective: "Objective 6", intenFixIncPercent: "60", intenStockPercent: "20", intenFilPercent: "20" },
    { id: 7, name: "John Doe", objective: "Objective 7", intenFixIncPercent: "70", intenStockPercent: "10", intenFilPercent: "20" },
    { id: 8, name: "Jane Johnson", objective: "Objective 8", intenFixIncPercent: "80", intenStockPercent: "10", intenFilPercent: "10" },
    { id: 9, name: "John Smith", objective: "Objective 9", intenFixIncPercent: "90", intenStockPercent: "10", intenFilPercent: "0" },
    { id: 10, name: "Jane Smith", objective: "Objective 10", intenFixIncPercent: "100", intenStockPercent: "0", intenFilPercent: "0" },
    { id: 11, name: "John Johnson", objective: "Objective 11", intenFixIncPercent: "0", intenStockPercent: "100", intenFilPercent: "0" }
];

const tableCols: TableColumn<Wallet>[] = [
    {
        name: 'Nome',
        selector: (row: InterfaceUserTableData) => row.name,
        sortable: true,
    },
    {
        name: 'Objetivo',
        selector: (row: InterfaceUserTableData) => row.objective,
        sortable: true,
    },
    {
        name: 'Renda Fixa % Obj',
        selector: (row: InterfaceUserTableData) => row.intenFixIncPercent,
        sortable: true,
    },
    {
        name: 'Ações % Obj',
        selector: (row: InterfaceUserTableData) => row.intenStockPercent,
        sortable: true,
    },
    {
        name: 'Fundos Imobiliários % Obj',
        selector: (row: InterfaceUserTableData) => row.intenFilPercent,
        sortable: true,
    },
    // Add a button to access the wallet
    {
        name: 'Acessar Carteira',
        cell: (row: InterfaceUserTableData) => <Link href={`/dashboard/wallet/${row.id}`} className={"text-blue-500"}><span>Acessar Carteira</span></Link>,
        sortable: false,
    }
];

const tableColsFixedInvestmentInterest: TableColumn<FixedInvestmentInterest>[] = [
    {
        name: 'Instituição',
        selector: (row: FixedInvestmentInterest) => row.institution,
        sortable: true,
    },
    {
        name: 'Valor Investido',
        selector: (row: FixedInvestmentInterest) => row.value,
        sortable: true,
    },
    {
        name: 'Valor Atual',
        selector: (row: FixedInvestmentInterest) => row.currentValue,
        sortable: true,
    },
    {
        name: 'Ganho',
        selector: (row: FixedInvestmentInterest) => row.gain,
        cell: (row: FixedInvestmentInterest) => {
            const gain = row.gain;
            const isPositive = gain > 0;

            return (
                <span
                    className={`font-bold ${isPositive ? 'text-green-500' : 'text-red-500'}`}
                >
                    {gain}$
                </span>
            );
        },
        sortable: true,
    },
    {
        name: 'Ganho %',
        selector: (row: FixedInvestmentInterest) => row.percentageGain,
        cell: (row: FixedInvestmentInterest) => {
            const percentage = row.percentageGain;
            const isPositive = percentage > 0;

            return (
                <span
                    className={`font-bold ${isPositive ? 'text-green-500' : 'text-red-500'}`}
                >
                    {percentage}%
                </span>
            );
        },
        sortable: true,
    },
];

const getVariableTransactionsTableCols = (walletId: string | string[], deleteTransaction: (walletId: string, transactionId: string) => void): TableColumn<VariableIncomeTransaction>[] => [
    {
        name: 'Ticker',
        selector: (row: VariableIncomeTransaction) => row.ticker.id,
        sortable: true,
    },
    {
        name: 'Compra / Venda',
        selector: (row: VariableIncomeTransaction) => convertBuyOrSaleNumberToString(row.buyOrSale),
        sortable: true,
    },
    {
        name: 'Data',
        selector: (row: VariableIncomeTransaction) => formatDate(row.date),
        sortable: true,
    },
    {
        name: 'Qtd Ações',
        selector: (row: VariableIncomeTransaction) => row.amount,
        sortable: true,
    },
    {
        name: 'Preço',
        selector: (row: VariableIncomeTransaction) => row.price,
        sortable: true,
    },
    {
        name: 'Total',
        selector: (row: VariableIncomeTransaction) => row.price * row.amount + '$',
        sortable: true,
    },
    {
        name: <span className="text-center w-full block">Ações</span>,
        cell: (row: VariableIncomeTransaction) => (
            <div className="flex justify-center w-full text-center">
                <CustomIconButton
                    icon={<FaRegTrashAlt />}
                    onClick={() => deleteTransaction(walletId as string, row.id.toString())}
                />
            </div>
        ),
        sortable: false,
    },
];

const getFixedTransactionsTableCols = (walletId: string | string[], deleteTransaction: (walletId: string, transactionId: string) => void): TableColumn<FixedIncomeTransaction>[] => [
    {
        name: 'Tipo',
        selector: (row: FixedIncomeTransaction) => row.type,
        sortable: true,
    },
    {
        name: 'Index',
        selector: (row: FixedIncomeTransaction) => row.indexName,
        sortable: true,
    },
    {
        name: 'Instituição',
        selector: (row: FixedIncomeTransaction) => row.institution,
        sortable: true,
    },
    {
        name: 'Data de Início - Data de Fim',
        selector: (row: FixedIncomeTransaction) => `${formatDate(row.startDate)} - ${formatDate(row.endDate)}`,
        sortable: true,
    },
    {
        name: 'Valor / Taxa %',
        selector: (row: FixedIncomeTransaction) => `${row.value} / ${row.taxValue}`,
        sortable: true,
    },
    {
        name: <span className="text-center w-full block">Actions</span>,
        cell: (row: FixedIncomeTransaction) => (
            <div className="flex justify-center w-full text-center">
                <CustomIconButton
                    icon={<FaRegTrashAlt />}
                    onClick={() => deleteTransaction(walletId as string, row.id.toString())}
                />
            </div>
        ),
        sortable: false,
    },
];

const tableColsVariableTransactionsInterest: TableColumn<VariableIncomeTransaction>[] = [
    {
        name: 'Ticker',
        selector: (row: VariableIncomeTransaction) => row.ticker.id,
        sortable: true,
    },
    {
        name: 'Compra / Venda',
        selector: (row: VariableIncomeTransaction) => convertBuyOrSaleNumberToString(row.buyOrSale),
        sortable: true,
    },
    {
        name: 'Data',
        selector: (row: VariableIncomeTransaction) => formatDate(row.date),
        sortable: true,
    },
    {
        name: 'Qtd Ações',
        selector: (row: VariableIncomeTransaction) => row.amount,
        sortable: true,
    },
    {
        name: 'Preço Compra',
        selector: (row: VariableIncomeTransaction) => row.price,
        sortable: true,
    },
    {
        name: 'Preço Atual',
        cell: (row: VariableIncomeTransaction) => {
            let lastVal = row.ticker.prices[0].adjustedClose;
            let paid = row.price;
            let percentage = ((lastVal - paid) / paid) * 100;
            percentage = Math.round(percentage * 100) / 100;
            const isPositive = percentage > 0;
            return (
                <span
                    className={`font-bold ${isPositive ? 'text-green-500' : 'text-red-500'}`}
                >
                    {lastVal}$
                </span>
            );
        },
        sortable: true,
    },
    {
        name: '% Atual',
        cell: (row: VariableIncomeTransaction) => {
            let lastVal = row.ticker.prices[0].adjustedClose;
            let paid = row.price;
            let percentage = ((lastVal - paid) / paid) * 100;
            percentage = Math.round(percentage * 100) / 100;
            const isPositive = percentage > 0;
            return (
                <span
                    className={`font-bold ${isPositive ? 'text-green-500' : 'text-red-500'}`}
                >
                    {percentage}%
                </span>
            );
        },
        sortable: true,
    },
];

export { tableColsVariableTransactionsInterest, tableData, tableCols, getFixedTransactionsTableCols, getVariableTransactionsTableCols, tableColsFixedInvestmentInterest };
export type { InterfaceUserTableData };