"use client";

import React, { useEffect, useState } from "react";
import { Wallet } from "@/config/interfaces";
import { convertBuyOrSaleNumberToString, formatDate } from "@/config/helpers";
import { CiViewList } from "react-icons/ci";
import CustomIconButton from "@/components/Button/CustomIconButton";

interface Transaction {
    wallet: Wallet | null;
    qnt: number;
}

export default function VariableTransactionsPreview({ wallet, qnt = 5 }: Transaction) {

    const [transactions, setTransactions] = useState<Wallet | null>(null);

    useEffect(() => {
        setTransactions(wallet);
    }, [wallet]);

    useEffect(() => {
        transactions?.variableIncomeTransactions.sort((a, b) => a.date > b.date ? -1 : 1);
        transactions?.variableIncomeTransactions.splice(qnt);
    }, [transactions, qnt]);

    if (!transactions) {
        return (
            <div className="flex flex-col items-center scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100 overflow-y-auto max-h-96">
                <h1 className="text-2xl font-semibold mb-3">No Variable Income Transactions</h1>
            </div>
        )
    }

    return (
        <div className="flex flex-col items-center scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100 overflow-y-auto max-h-96">
            <h1 className="text-2xl font-semibold mb-3">
                Variable Income Transactions 
                <CustomIconButton icon={<CiViewList size={20} color="black"/>} />
            </h1>
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 rounded-lg">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 rouded-t-lg">
                    <tr>
                        <th scope="col" className="px-6 py-3 text-center">
                            Instituition
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                            Value
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                            Action
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                            Date
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {transactions.variableIncomeTransactions.map(element => (
                        <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={Math.random()}>
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 text-center">
                                {element.ticker.ticker}
                            </th>
                            <td className="px-6 py-4 text-center">
                                {element.amount} - {element.price} each
                            </td>
                            <td className="px-6 py-4 text-center">
                                {convertBuyOrSaleNumberToString(element.buyOrSale)}
                            </td>
                            <td className="px-6 py-4 text-center">
                                {formatDate(element.date)}
                            </td>
                        </tr >
                    ))}
                </tbody>
            </table>
        </div>
    );
}