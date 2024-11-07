"use client"

import React from "react";
import { useParams } from "next/navigation";
import Card from "@/components/Layout/Card";
import VariableTransactionsTable from "@/components/Table/VariableTransactions/VariableTransactionsTable";

export default function Home() {
    const { walletId } = useParams() as { walletId: string};

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <h1 className="text-2xl font-semibold">
                    Manage Variable Transactions
                </h1>
            </Card>
            <Card colspan={12} rowspan={1}>
                <VariableTransactionsTable customColSpan={12} customRowSpan={1} />
            </Card>
            
        </>
    );
}
