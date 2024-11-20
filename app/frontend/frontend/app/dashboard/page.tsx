"use client";

import React, { useEffect } from "react";
import {useUser} from "@/userContext";
import Line from "@/components/Charts/Line/Line";
import Card from "@/components/Layout/Card";
import WalletsTable from "@/components/Table/Wallet/WalletTable";

export default function Home() {
    const {getUserData, userId, getUserPassword} = useUser();

    useEffect(() => {
        console.log("User data", getUserData());
        console.log("User id", userId);
        console.log("User password", getUserPassword());
    }, []);

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className="flex justify-between items-center text-2xl font-semibold">
                    Escolha ou crie uma nova carteira para começar
                </div>
            </Card>
            <Card colspan={12} rowspan={1}>
                <WalletsTable customColSpan={12} customRowSpan={1} />
            </Card>
        </>
    );
}
