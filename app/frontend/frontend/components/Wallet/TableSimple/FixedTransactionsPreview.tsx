"use client";

import React, { useEffect, useState } from "react";
import { Wallet } from "@/config/interfaces";
import { formatDate } from "@/config/helpers";
import CustomIconButton from "@/components/Button/CustomIconButton";
import { CiViewList } from "react-icons/ci";
import { useParams } from "next/navigation";
import { useRouter } from "next/navigation";

interface Transaction {
    wallet: Wallet | null;
    qnt: number;
}

export default function FixedTransactionsPreview({ wallet, qnt = 5 }: Transaction) {
    const router = useRouter();
    const { walletId } = useParams();

    const [transactions, setTransactions] = useState<Wallet | null>(null);

    useEffect(() => {
        setTransactions(wallet);
    }, [wallet]);

    useEffect(() => {
        transactions?.fixedIncomeTransactions.sort((a, b) => a.startDate > b.startDate ? -1 : 1);
        transactions?.fixedIncomeTransactions.splice(qnt);
    }, [transactions, qnt]);

    if (!transactions) {
        return (
            <div className="flex flex-col items-center scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100 overflow-y-auto max-h-96">
                <h1 className="text-2xl font-semibold mb-3">No Fixed Income Transactions</h1>
            </div>
        )
    }

    return (
        <div className="flex flex-col items-center max-h-96">
            <h1
                className="text-2xl font-semibold mb-3 hover:cursor-pointer"
                onClick={() => router.push(`/dashboard/wallet/${walletId}/tfixed`)}
            >
                Transações de Renda Fixa
            </h1>
            <div className="overflow-y-auto max-h-80 w-full scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100 rounded-lg">
                <table className="w-full text-sm text-left rtl:text-right text-gray-500">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 rouded-t-lg">
                        <tr>
                            <th scope="col" className="px-6 py-3 text-center">
                                Instituição
                            </th>
                            <th scope="col" className="px-6 py-3 text-center">
                                Ativo
                            </th>
                            <th scope="col" className="px-6 py-3 text-center">
                                Total
                            </th>
                            <th scope="col" className="px-6 py-3 text-center">
                                Quantidade
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {transactions.fixedIncomeTransactions.map(element => (
                            <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={Math.random()}>
                                <th scope="row" className="px-6 py-4 font-medium text-gray-900 text-center">
                                    {element.institution}
                                </th>
                                <td className="px-6 py-4 text-center">
                                    {element.indexName}
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {element.value}
                                </td>
                                <td className="px-6 py-4 text-center">
                                    {formatDate(element.startDate)} - {formatDate(element.endDate)}
                                </td>
                            </tr >
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}