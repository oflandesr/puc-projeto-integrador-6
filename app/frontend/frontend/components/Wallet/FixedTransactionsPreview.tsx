"use client";

import React, { useEffect, useState } from "react";
import { Wallet } from "@/config/interfaces";
import { formatDate } from "@/config/helpers";

interface Transaction {
    wallet: Wallet | null;
}

export default function FixedTransactionsPreview({ wallet }: Transaction) {
    return (
        <div className="flex flex-col items-center scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100 overflow-y-auto max-h-96">
            <h1 className="text-2xl font-semibold mb-3">Fixed Income Transactions</h1>
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 rounded-lg">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 rouded-t-lg">
                    <tr>
                        <th scope="col" className="px-6 py-3 text-center">
                            Instituition
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                            Index
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                            Value
                        </th>
                        <th scope="col" className="px-6 py-3 text-center">
                            Time
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {wallet?.fixedIncomeTransactions.map(element => (
                        <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700" key={element.id}>
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
    );
}