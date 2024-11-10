"use client";

import React from "react";
import Card from "@/components/Layout/Card";
import { TickerExtendedName } from "@/config/helpers";
import { Ticker } from "@/config/interfaces";

interface InfoTickerTableProps {
    ticker: Ticker | null;
}
// TODO: Arrumar essa bosta
export default function InfoTickerTable({ ticker }: InfoTickerTableProps) {

    if (!ticker) {
        return (
            <Card colspan={12} rowspan={1}>
                Ticker not found
            </Card>
        );
    }

    return (
        <div className="relative overflow-x-auto sm:rounded-lg col-span-12">
            <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                        <th scope="col" className="px-6 py-4">
                            Detail
                        </th>
                        <th scope="col" className="py-3">
                            Value
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        // Loop trough the object and show the values
                        ticker && Object.entries(ticker).map(([key, value]) => {
                            if (!(key in TickerExtendedName)) {
                                return null;
                            }

                            return (
                                <tr key={key} className="bg-gray-50 odd:bg-white dark:bg-gray-800 hover:bg-gray-100 dark:hover:bg-gray-700">
                                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-gray-200">
                                        {TickerExtendedName[key as keyof typeof TickerExtendedName] || key}
                                    </td>
                                    <td className="py-4 whitespace-nowrap text-sm text-gray-500 dark:text-gray-400">
                                        {value}
                                    </td>
                                </tr>
                            );
                        })
                    }
                </tbody>
            </table>
        </div>
    );
}
