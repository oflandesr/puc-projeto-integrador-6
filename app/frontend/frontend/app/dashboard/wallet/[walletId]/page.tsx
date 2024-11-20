"use client";

import React, {useEffect } from "react";
import {useUser} from "@/userContext"
import {useParams } from "next/navigation";
import Card from "@/components/Layout/Card";
import { Wallet} from "@/config/interfaces";
import useLazyGet from "@/hooks/Api/useLazyGet";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import WalletHeader from "@/components/Wallet/WalletHeader";
import FixedTransactionsPreview from "@/components/Wallet/TableSimple/FixedTransactionsPreview";
import VariableTransactionsPreview from "@/components/Wallet/TableSimple/VariableTransactionsPreview";
import WalletSummary from "@/components/Wallet/WalletSummary";

export default function Home() {

    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();
    const {
        error : walletError,
        loading : loadingWallet,
        data : walletData,
        fetchData : fetchWalletData
    } = useLazyGet<Wallet>(); 

    async function handleGetPageData() {
        try {
            const urlWallet = `/wallet/${walletId}`;

            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };

            await fetchWalletData(urlWallet, headers);
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        handleGetPageData();
    }, [getUserData, userId]);

    if (loadingWallet) {
        return (
            <Card colspan={12} rowspan={1}>
                <LoadingFullPage />
            </Card>
        )
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
                <WalletHeader walletData={walletData} />
            </Card>
            <Card colspan={12} rowspan={1}>
                {walletData && <WalletSummary walletData={walletData} />}
            </Card>
            <Card colspan={6} rowspan={1}>
                <FixedTransactionsPreview wallet={walletData} qnt={5} />
            </Card>
            <Card colspan={6} rowspan={1}>
                <VariableTransactionsPreview wallet={walletData} qnt={5} />
            </Card>
        </>
    );
}