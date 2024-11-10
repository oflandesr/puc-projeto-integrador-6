import Link from "next/link";
import {TableColumn} from "react-data-table-component";
import {FixedIncomeTransaction, VariableIncomeTransaction, Wallet} from "@/config/interfaces";
import { formatDate } from "@/config/helpers";
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
    {id: 11, name: "John Johnson", objective: "Objective 11", intenFixIncPercent: "0", intenStockPercent: "100", intenFilPercent: "0"}
];

const tableCols : TableColumn<Wallet>[] = [
    {
        name: 'Wallet Name',
        selector: (row: InterfaceUserTableData) => row.name,
        sortable: true,
    },
    {
        name: 'Objective',
        selector: (row: InterfaceUserTableData) => row.objective,
        sortable: true,
    },
    {
        name: 'Inten Fix Inc %',
        selector: (row: InterfaceUserTableData) => row.intenFixIncPercent,
        sortable: true,
    },
    {
        name: 'Inten Stock %',
        selector: (row: InterfaceUserTableData) => row.intenStockPercent,
        sortable: true,
    },
    {
        name: 'Inten Fil %',
        selector: (row: InterfaceUserTableData) => row.intenFilPercent,
        sortable: true,
    },
    // Add a button to access the wallet
    {
        name: 'Access',
        cell: (row: InterfaceUserTableData) => <Link href={`/dashboard/wallet/${row.id}`} className={"text-blue-500"}><span>Access Wallet</span></Link>,
        sortable: false,
    }
];

const getVariableTransactionsTableCols = (walletId: string | string[], deleteTransaction: (walletId: string, transactionId: string) => void): TableColumn<VariableIncomeTransaction>[] => [
    {
        name: 'Ticker',
        selector: (row: VariableIncomeTransaction) => row.ticker.id,
        sortable: true,
    },
    {
        name: 'Buy / Sale',
        selector: (row: VariableIncomeTransaction) => row.buyOrSale === 0 ? 'Buy' : 'Sale',
        sortable: true,
    },
    {
        name: 'Date',
        selector: (row: VariableIncomeTransaction) => formatDate(row.date),
        sortable: true,
    },
    {
        name: 'Amount',
        selector: (row: VariableIncomeTransaction) => row.amount,
        sortable: true,
    },
    {
        name: 'Price',
        selector: (row: VariableIncomeTransaction) => row.price,
        sortable: true,
    },
    {
        name: 'Total',
        selector: (row: VariableIncomeTransaction) => row.price * row.amount + '$',
        sortable: true,
    },
    {
        name: <span className="text-center w-full block">Actions</span>,
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
        name: 'Type',
        selector: (row: FixedIncomeTransaction) => row.type,
        sortable: true,
    },
    {
        name: 'Index Name',
        selector: (row: FixedIncomeTransaction) => row.indexName,
        sortable: true,
    },
    {
        name: 'Institution',
        selector: (row: FixedIncomeTransaction) => row.institution,
        sortable: true,
    },
    {
        name: 'Start / End Date',
        selector: (row: FixedIncomeTransaction) => `${formatDate(row.startDate)} - ${formatDate(row.endDate)}`,
        sortable: true,
    },
    {
        name: 'Value / Tax Value',
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

export { tableData, tableCols, getFixedTransactionsTableCols , getVariableTransactionsTableCols };
export type { InterfaceUserTableData };
