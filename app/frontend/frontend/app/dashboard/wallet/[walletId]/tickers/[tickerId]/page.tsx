"use client";

import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import ErrorFullPage from "@/components/Layout/ErrorFullPage";
import HeaderCards from "@/components/Ticker/Header/HeaderCards";
import InfoTickerTable from "@/components/Ticker/TableSimple/InfoTickerTable";
import useFetchTicker from "@/hooks/useFetchTicker";
import TickerActions from "@/components/Ticker/Extra/TickerActions";
import TickerHeader from "@/components/Ticker/Header/TickerHeader";

export default function Home() {
    const router = useRouter();
    const { walletId, tickerId } = useParams() as { walletId: string; tickerId: string };
    const { ticker, loading, error } = useFetchTicker(tickerId);

    if (loading) return <LoadingFullPage />;
    if (error) return <ErrorFullPage error={error} />;
    if (!ticker) return <Card colspan={12} rowspan={1}>Ticker not found</Card>;

    return (
        <>
            <TickerHeader ticker={ticker} />
            <HeaderCards ticker={ticker} />
            <Card colspan={12} rowspan={1}>
                Graph Will Be Here
            </Card>
            <TickerActions walletId={walletId} tickerId={tickerId} />
            <InfoTickerTable ticker={ticker} />
        </>
    );
}
