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
            <Card colspan={6} rowspan={1}>
                Random Line Chart
            </Card>
            <Card colspan={6} rowspan={1}>
                Random Line Chart
            </Card>
            <Card colspan={12} rowspan={1}>
                <WalletsTable customColSpan={12} customRowSpan={1} />
            </Card>
        </>
    );
}
