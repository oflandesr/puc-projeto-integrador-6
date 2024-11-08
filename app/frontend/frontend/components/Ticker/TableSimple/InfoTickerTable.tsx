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
                        ticker && Object.entries(ticker).map(([key, value]) => (
                            <tr key={key} className="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700 hover:bg-gray-100">
                                <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                {
                                    // Check if `key` is a key of TickerExtendedName and then retrieve the value
                                    key in TickerExtendedName 
                                    ? TickerExtendedName[key as keyof typeof TickerExtendedName] 
                                    : key
                                }
                                </th>
                                <td className=" py-4">
                                    {value}
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    );
}
