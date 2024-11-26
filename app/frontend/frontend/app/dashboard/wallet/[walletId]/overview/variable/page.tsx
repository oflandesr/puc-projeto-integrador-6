"use client";

import React, {useEffect } from "react";
import {useUser} from "@/userContext"
import {useParams } from "next/navigation";
import Card from "@/components/Layout/Card";
import { Wallet} from "@/config/interfaces";
import useLazyGet from "@/hooks/API/useLazyGet";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import WalletHeader from "@/components/Wallet/WalletHeader";
import FixedTransactionsPreview from "@/components/Wallet/TableSimple/FixedTransactionsPreview";
import VariableTransactionsPreview from "@/components/Wallet/TableSimple/VariableTransactionsPreview";
import WalletSummary from "@/components/Wallet/WalletSummary";
import WalletCurrentDistribution from "@/components/Wallet/WalletCurrentDistribution";
import Table from "@/components/Table/Table";
import { tableColsVariableTransactionsInterest } from "@/mock/mock";

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
                <div className="text-2xl font-semibold">
                    Investimentos Variáveis - Resumo
                </div>
            </Card>
            {(walletData && walletData.variableIncomeTransactions) && (
                <Card colspan={12} rowspan={1}>
                    <Table columns={tableColsVariableTransactionsInterest} data={walletData.variableIncomeTransactions} />
                </Card>
            )}
        </>
    );
}