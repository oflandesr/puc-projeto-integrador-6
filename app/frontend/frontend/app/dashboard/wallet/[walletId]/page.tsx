"use client";

import React, { useEffect } from "react";
import {useUser} from "@/userContext"
import {useParams} from "next/navigation";
import Card from "@/components/Layout/Card";

export default function Home() {
    const {getUserData, userId} = useUser();
    // Get the walletId from the router wallet/:walletId
    const { walletId } = useParams();


    useEffect(() => {
        console.log(getUserData());
    }, [getUserData, userId]);

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <h1>Wallet Id {walletId}</h1>
            </Card>
        </>
    );
}
