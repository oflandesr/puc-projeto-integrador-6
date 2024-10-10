"use client";

import React, {useEffect, useState} from "react";
import {useUser} from "@/userContext";
import Card from "@/components/Layout/Card";
import CustomButton from "@/components/CustomButton";
import CustomInput from "@/components/CustomInput";

/*
    {
      "user": 0, // User id by default
      "name": "string", // wallet name
      "objective": "string", // wallet objective
      "intenFixIncPercent": "string", // this is a percentage
      "intenStockPercent": "string", // this is a percentage
      "intenFilPercent": "string" // this is a percentage
    }
*/

interface InterfaceWalletData {
    user: number;
    name: string;
    objective: string;
    intenFixIncPercent: string;
    intenStockPercent: string;
    intenFilPercent: string;
}

export default function Home() {
    const {getUserData, userId} = useUser();

    const [walletData, setWalletData] = useState<InterfaceWalletData>({
        user: 0,
        name: "",
        objective: "",
        intenFixIncPercent: "",
        intenStockPercent: "",
        intenFilPercent: ""
    });

    function handleDataSubmit() {
        if (isWalletDataValid()) {
            console.log(walletData);
        } else {
            console.error("Invalid data");
        }
    }

    function isWalletDataValid() : boolean {
        try {
            const sum: number = parseInt(walletData.intenFixIncPercent) + parseInt(walletData.intenStockPercent) + parseInt(walletData.intenFilPercent);
            if (sum !== 100) {
                return false;
            }
            return walletData.name !== "" && walletData.objective !== "" && walletData.intenFixIncPercent !== "" && walletData.intenStockPercent !== "" && walletData.intenFilPercent !== "";
        } catch (e) {
            console.error(e);
            return false;
        }
    }

    useEffect(() => {
        console.log(getUserData());
    }, [getUserData, userId]);

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className={"grid grid-cols-12 gap-4"}>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Wallet Name"} value={walletData.name} onChange={(e) => setWalletData({...walletData, name: e.target.value})} type={'text'} name={'name'} id={'name'}/>
                    </div>
                    <CustomInput placeholder={"Wallet Objective"} value={walletData.objective} onChange={(e) => setWalletData({...walletData, objective: e.target.value})} type={'text'} name={'objective'} id={'objective'}/>
                    <CustomInput placeholder={"Intentional Fixed Income Percentage"} value={walletData.intenFixIncPercent} onChange={(e) => setWalletData({...walletData, intenFixIncPercent: e.target.value})} type={'number'} name={'intenFixIncPercent'} id={'intenFixIncPercent'}/>
                    <CustomInput placeholder={"Intentional Stock Percentage"} value={walletData.intenStockPercent} onChange={(e) => setWalletData({...walletData, intenStockPercent: e.target.value})} type={'number'} name={'intenStockPercent'} id={'intenStockPercent'} />
                    <CustomInput
                        placeholder={"Intentional Fixed Income Percentage"}
                        value={walletData.intenFilPercent}
                        onChange={(e) => setWalletData({...walletData, intenFilPercent: e.target.value})}
                        type={'number'}
                        name={'intenFilPercent'}
                        id={'intenFilPercent'}
                    />
                    <CustomButton type={"button"} onClick={handleDataSubmit}>
                        <span>Create Wallet</span>
                    </CustomButton>
                </div>
            </Card>
        </>
    );
}
