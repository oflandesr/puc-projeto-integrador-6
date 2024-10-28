"use client";

import React from "react";
import { useUser } from "@/userContext";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import { Ticker } from "@/config/interfaces";

interface TickerItemProps {
    ticker: Ticker;
    id: string; // Use a different prop name for the unique identifier
}

export default function TickerItem({ id, ticker }: TickerItemProps) {
    const router = useRouter();
    const { getUserData, userId, getUserPassword } = useUser();
    const { walletId } = useParams();

    function handleClick() {
        try {
            const url = `/dashboard/wallet/${walletId}/tickers/${ticker.ticker}`;
            router.push(url);
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <Card colspan={12} rowspan={1} className="hover:cursor-pointer hover:shadow-md sm:col-span-6 lg:col-span-3">
            <div onClick={handleClick} className="max-w bg-white rounded-lg overflow-hidden">
                <div className="flex flex-col items-center py-4">
                    {/* Ticker Name in place of the image */}
                    <h5 className="text-3xl font-bold text-gray-900">{ticker.ticker}</h5>
                    {/* Company Name */}
                    <p className="text-gray-500 text-sm">{ticker.shortName}</p>
                </div>
                <hr className="border-gray-300 w-full" />
                <div className="p-4">
                    {/* 2x2 Grid for Market Data */}
                    <div className="grid grid-cols-2 gap-4 text-center">
                        <div>
                            <p className="text-gray-500 text-sm font-semibold">High</p>
                            <p className="text-gray-900 text-lg font-semibold">
                                {ticker.regularMarketDayHigh}
                            </p>
                        </div>
                        <div>
                            <p className="text-gray-500 text-sm font-semibold">Low</p>
                            <p className="text-gray-900 text-lg font-semibold">
                                {ticker.regularMarketDayLow}
                            </p>
                        </div>
                        <div>
                            <p className="text-gray-500 text-sm font-semibold">Reg. Price</p>
                            <p className="text-gray-900 text-lg font-semibold">
                                {ticker.regularMarketPrice}
                            </p>
                        </div>
                        <div>
                            <p className="text-gray-500 text-sm font-semibold">Open. Price</p>
                            <p className="text-gray-900 text-lg font-semibold">
                                {ticker.regularMarketOpen}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </Card>
    );
}
