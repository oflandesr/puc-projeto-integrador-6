"use client";

import React, { useEffect, useState } from "react";
import { useUser } from "@/userContext";
import Card from "@/components/Layout/Card";
import Table from "@/components/Table/Table";
import { getFixedTransactionsTableCols, getVariableTransactionsTableCols, tableCols } from "@/mock/mock";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import useLazyGet from "@/hooks/Api/useLazyGet";
import { Wallet } from "@/config/interfaces";
import { useParams } from "next/navigation";
import useGet from "@/hooks/Api/useGet";
import useDelete from "@/hooks/Api/useDelete";

interface WalletsTableProps {
    customColSpan: number;
    customRowSpan: number;
    parentRefresh?: boolean;
}

const VariableTransactionsTable = ({ customColSpan, customRowSpan, parentRefresh}: WalletsTableProps) => {
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();
    const [refresh, setRefresh] = useState<boolean>(false);

    const {
        error: walletError,
        loading: walletLoading,
        fetchData: fetchWallets,
        data: walletData,
    } = useLazyGet<Wallet>();

    const {
        error: deleteError,
        loading: deleteLoading,
        deleteData: deleteTransaction,
    } = useDelete<null>();

    async function getWallets() {
        try {
            const url = `/wallet/${walletId}`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await fetchWallets(url, headers);
        } catch (err) {
            console.error(err);
        }
    }

    async function handleDeleteTransaction(waletId: string, transactionId: string) {
        try {
            const url = `/wallet/${walletId}/tvariable/${transactionId}`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await deleteTransaction(url, headers);
            setRefresh(!refresh);
        } catch (err) {
            alert("Error deleting transaction");
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            await getWallets();
        };
        fetchData();
    }, [getUserData, userId, parentRefresh, refresh]);

    if (walletLoading || deleteLoading) {
        return <LoadingFullPage />;
    }

    if (walletError || !walletData || !walletData.fixedIncomeTransactions) {
        return (
            <Card colspan={customColSpan} rowspan={customRowSpan}>
                <div>Error loading wallets</div>
            </Card>
        )
    }

    if (deleteError) {
        return (
            <Card colspan={customColSpan} rowspan={customRowSpan}>
                <div className="text-red-500">Error deleting transaction</div>
                {deleteError}
            </Card>
        )
    }

    return (
        <>
            {walletData ? (
                <Table columns={getVariableTransactionsTableCols(walletId, handleDeleteTransaction)} data={walletData.variableIncomeTransactions} />
            ) : (
                <div>No wallets to display</div>
            )}
        </>
    );
};

export default VariableTransactionsTable;
