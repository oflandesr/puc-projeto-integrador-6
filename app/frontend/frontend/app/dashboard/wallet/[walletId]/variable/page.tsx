"use client"

import React from "react";
import { useParams } from "next/navigation";
import Card from "@/components/Layout/Card";
import VariableTransactionsTable from "@/components/Table/VariableTransactions/VariableTransactionsTable";
import Header from "@/components/Layout/Header";

export default function Home() {
    const { walletId } = useParams() as { walletId: string};

    return (
        <>
            <Header title="Investimentos Variáveis" />
            <Card colspan={12} rowspan={1}>
                <VariableTransactionsTable customColSpan={12} customRowSpan={1} />
            </Card>
            
        </>
    );
}
