"use client";

import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import useFetchTicker from "@/hooks/useFetchTicker";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import { AddVariableIncomeTransaction } from "@/config/interfaces";

export default function Home() {
    const router = useRouter();
    const { walletId, tickerId } = useParams() as { walletId: string; tickerId: string };
    const { 
        ticker: getTicker,
        loading: loadingTicker,
        error: errorTicker
    } = useFetchTicker(tickerId);

    const [variableIncomeParams, setVariableIncomeParams] = useState<AddVariableIncomeTransaction>({
        ticker: tickerId,
        buyOrSale: 0,
        date: "",
        amount: 0,
        price: 0
    });

    async function handleAddVariableTransaction() {
        try {
            // /wallet/:walletId/tvariable and params is 
            // TODO: Complete this 
        } catch (e) {
            console.error(e);
        }
    }

    if (loadingTicker) return <LoadingFullPage />;
    if (errorTicker) return <Card colspan={12} rowspan={1}>Ticker not found</Card>;

    return (
        <>
            <Card colspan={12} rowspan={1}>
                Add Variable Transaction {tickerId} to Wallet {walletId}
            </Card>
        </>
    );
}
