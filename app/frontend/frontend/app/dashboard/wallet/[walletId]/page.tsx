"use client";

import React, { useEffect } from "react";
import {useUser} from "@/userContext"
import {useParams} from "next/navigation";

export default function Home() {
    const {getUserData, userId} = useUser();
    // Get the walletId from the router wallet/:walletId
    const { walletId } = useParams();


    useEffect(() => {
        console.log(getUserData());
    }, [getUserData, userId]);

    return (
        <>
            Wallet Info { walletId }
        </>
    );
}
