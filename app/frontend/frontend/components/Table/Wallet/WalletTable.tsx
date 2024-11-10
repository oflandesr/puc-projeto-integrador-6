"use client";

import React, { useEffect } from "react";
import { useUser } from "@/userContext";
import Card from "@/components/Layout/Card";
import Table from "@/components/Table/Table";
import { tableCols } from "@/mock/mock";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import useLazyGet from "@/hooks/Api/useLazyGet";
import { Wallet } from "@/config/interfaces";

interface WalletsTableProps {
    customColSpan: number;
    customRowSpan: number;
    parentRefresh?: boolean;
}

const WalletsTable = ({ customColSpan, customRowSpan, parentRefresh}: WalletsTableProps) => {
    const { getUserData, userId, getUserPassword } = useUser();

    const {
        error: walletError,
        loading: walletLoading,
        fetchData: fetchWallets,
        data: walletData,
    } = useLazyGet<Wallet[]>();

    async function getWallets() {
        try {
            const url = `/wallet?userId=${userId}`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await fetchWallets(url, headers);
        } catch (err) {
            console.error(err);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            await getWallets();
        };
        fetchData();
    }, [getUserData, userId, parentRefresh]);

    if (walletLoading) {
        return <LoadingFullPage />;
    }

    if (walletError) {
        return (
            <Card colspan={customColSpan} rowspan={customRowSpan}>
                <div>Error loading wallets</div>
            </Card>
        )
    }

    return (
        <>
            {walletData ? (
                <Table columns={tableCols} data={walletData} />
            ) : (
                <div>No wallets to display</div>
            )}
        </>
    );
};

export default WalletsTable;
