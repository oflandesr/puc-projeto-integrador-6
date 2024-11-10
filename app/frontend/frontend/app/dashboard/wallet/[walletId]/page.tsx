"use client";

import React, {useEffect, useState} from "react";
import {useUser} from "@/userContext"
import {useParams, useRouter} from "next/navigation";
import Card from "@/components/Layout/Card";
import { Index, Wallet} from "@/config/interfaces";
import useLazyGet from "@/hooks/API/useLazyGet";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import WalletHeader from "@/components/Wallet/WalletHeader";
import Circle from "@/components/Charts/Circle/Circle";
import FixedTransactionsPreview from "@/components/Wallet/TableSimple/FixedTransactionsPreview";
import VariableTransactionsPreview from "@/components/Wallet/TableSimple/VariableTransactionsPreview";
import FixedTransactionChart from "@/components/Charts/Fixed/FixedTransactionsChart";

export default function Home() {

    const router = useRouter();
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();

    const [series, setSeries] = useState<number[]>([]);

    const {
        error : walletError,
        loading : loadingWallet,
        data : walletData,
        fetchData : fetchWalletData
    } = useLazyGet<Wallet>();

    async function handleGetWalletData() {
        try {
            const url = `/wallet/${walletId}`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await fetchWalletData(url, headers);
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        handleGetWalletData();
    }, [getUserData, userId]);

    useEffect(() => {
        if (!walletData || !walletData?.intenFixIncPercent || !walletData?.intenStockPercent || !walletData?.intenFilPercent) {
            return;
        }
        setSeries([
            parseInt(walletData.intenFixIncPercent ?? "0"),
            parseInt(walletData.intenStockPercent ?? "0"),
            parseInt(walletData.intenFilPercent ?? "0"),
        ]);
    }, [walletData]);

    if (loadingWallet) {
        return <LoadingFullPage />;
    }

    if (walletError) {
        return (
            <Card colspan={12} rowspan={1}>
                <div>Error loading wallet</div>
            </Card>
        );
    }

    return (
        <>
            <Card colspan={12} rowspan={1}>
                {/* Wallet header, will have the name here */}
                <WalletHeader walletData={walletData} />
            </Card>
            <Card colspan={6} rowspan={1}>
                {/* Wallet % distributed to each type of investment, fixed income, stock, fii */}
                <Circle
                    title=""
                    height={250}
                    onclick={() => {
                        router.push(`/dashboard/wallet/${walletId}/edit`);
                    }}
                    labels={["Fixed Income", "Stock", "FII"]}
                    data={series}
                    btnText="Editar"
                />
            </Card>
            <Card colspan={6} rowspan={1}>
                <FixedTransactionsPreview wallet={walletData} qnt={5} />
            </Card>
            <Card colspan={12} rowspan={1}>
                <VariableTransactionsPreview wallet={walletData} qnt={5} />
            </Card>
            <Card colspan={12} rowspan={1}>
                <FixedTransactionChart />
            </Card>
        </>
    );
}