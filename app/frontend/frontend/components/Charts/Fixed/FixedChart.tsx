"use client";

import React, { useEffect } from "react";
import { useUser } from "@/userContext"
import { useParams } from "next/navigation";
import Card from "@/components/Layout/Card";
import { Index, Wallet } from "@/config/interfaces";
import useLazyGet from "@/hooks/Api/useLazyGet";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import WalletHeader from "@/components/Wallet/WalletHeader";
import FixedTransactionsPreview from "@/components/Wallet/TableSimple/FixedTransactionsPreview";
import VariableTransactionsPreview from "@/components/Wallet/TableSimple/VariableTransactionsPreview";
import WalletSummary from "@/components/Wallet/WalletSummary";
import Line from "../Line/Line";

export default function FixedChart() {

    const { getUserData, userId, getUserPassword } = useUser();
    const { walletId } = useParams();
    const {
        error: indexError,
        loading: loadingIndex,
        data: indexData,
        fetchData: fetchIndexData
    } = useLazyGet<Index[]>();

    async function handleGetPageData() {
        try {
            const urlIndex = `/index`;

            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };

            await fetchIndexData(urlIndex, headers);
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        handleGetPageData();
    }, [getUserData, userId]);

    if (loadingIndex) {
        return <LoadingFullPage />;
    }

    if (indexError) {
        return (
            <Card colspan={4} rowspan={1}>
                <div>Error loading Index</div>
            </Card>
        );
    }

    if (!indexData) {
        return (
            <Card colspan={4} rowspan={1}>
                <div className="flex flex-col items-center scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100 overflow-y-auto max-h-96">
                    <h1 className="text-2xl font-semibold mb-3">No Fixed Income Transactions</h1>
                </div>
            </Card>
        )
    }

    return (
        <>
            <Card colspan={4} rowspan={1}>
                <Line
                    title="IPCA"
                    seriesName="IPCA"
                    height={300}
                    yAxis={indexData.map((index) => index.ipca ?? 0)}
                    xAxis={indexData?.map((index) => index.date)}
                    showLabel={false}
                    isBtnActive={false}
                    onClick={() => { }}
                    symbol="% ( IPCA )"
                />
            </Card>
            <Card colspan={4} rowspan={1}>
                <Line
                    title="CDI"
                    seriesName="CDI"
                    height={300}
                    yAxis={indexData.map((index) => index.cdi ?? 0)}
                    xAxis={indexData?.map((index) => index.date)}
                    showLabel={false}
                    isBtnActive={false}
                    onClick={() => { }}
                    symbol="% ( CDI )"
                />
            </Card>
            <Card colspan={4} rowspan={1}>
                <Line
                    title="SELIC"
                    seriesName="SELIC"
                    height={300}
                    yAxis={indexData.map((index) => index.selic ?? 0)}
                    xAxis={indexData?.map((index) => index.date)}
                    showLabel={false}
                    isBtnActive={false}
                    onClick={() => { }}
                    symbol="% ( SELIC )"
                />
            </Card>
        </>
    );
}