"use client"

import React from "react";
import { useParams } from "next/navigation";
import Card from "@/components/Layout/Card";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import useFetchTicker from "@/hooks/Api/useFetchTicker";
import VariableTransactionsTable from "@/components/Table/VariableTransactions/VariableTransactionsTable";
import { useVariableTransaction } from "@/hooks/Api/useVariableTransactions";
import VariableTransactionForm from "@/components/Ticker/Add/VariableTransactionForm";

export default function Home() {
    const { walletId, tickerId } = useParams() as { walletId: string; tickerId: string };
    const { ticker, loading: loadingTicker, error: errorTicker } = useFetchTicker(tickerId);
    const { handleTransaction, postError, postLoading, parentRefresh } = useVariableTransaction(walletId);

    if (loadingTicker) return <LoadingFullPage />;
    if (errorTicker) return <Card colspan={12} rowspan={1}>Ticker not found</Card>;

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <h1 className="text-2xl font-semibold">
                    Add Variable Transaction for <span className="text-blue-500">{tickerId}</span>
                </h1>
                <VariableTransactionForm tickerId={tickerId} onSubmit={handleTransaction} loading={postLoading} />
            </Card>
            {postError && <Card colspan={12} rowspan={1}>{postError}</Card>}
            <Card colspan={12} rowspan={1}>
                <VariableTransactionsTable customColSpan={12} customRowSpan={1} parentRefresh={parentRefresh} />
            </Card>
        </>
    );
}
