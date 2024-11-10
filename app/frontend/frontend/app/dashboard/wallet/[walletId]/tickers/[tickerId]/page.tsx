"use client";

import React, { use, useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import ErrorFullPage from "@/components/Layout/ErrorFullPage";
import HeaderCards from "@/components/Ticker/Header/HeaderCards";
import InfoTickerTable from "@/components/Ticker/TableSimple/InfoTickerTable";
import useFetchTicker from "@/hooks/useFetchTicker";
import TickerActions from "@/components/Ticker/Extra/TickerActions";
import TickerHeader from "@/components/Ticker/Header/TickerHeader";
import Line from "@/components/Charts/Line/Line";

export default function Home() {
    const router = useRouter();
    const { walletId, tickerId } = useParams() as { walletId: string; tickerId: string };
    const { ticker, loading, error } = useFetchTicker(tickerId);

    if (loading) return <LoadingFullPage />;
    if (error) return <ErrorFullPage error={error} />;
    if (!ticker) return <Card colspan={12} rowspan={1}>Ticker not found</Card>;

    console.log("ticker", ticker);

    return (
        <>
            <TickerHeader ticker={ticker} />
            <HeaderCards ticker={ticker} />
            <Card colspan={12} rowspan={1}>
                <Line 
                    title="Price" 
                    seriesName="Price"
                    height={250} 
                    isBtnActive={false}
                    onClick={() => {}}
                    showLabel={false}
                    yAxis={ticker.prices.map((p) => p.open)}
                    xAxis={ticker.prices.map((p) => {
                        const timestamp = Number(p.timestamp);
                        return new Date(timestamp * 1000).toLocaleDateString();
                    })}
                />
            </Card>
            <TickerActions walletId={walletId} tickerId={tickerId} />
            <InfoTickerTable ticker={ticker} />
        </>
    );
}
