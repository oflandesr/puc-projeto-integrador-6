"use client";

import React, {useEffect, useState} from "react";
import {useUser} from "@/userContext";
import Card from "@/components/Layout/Card";
import CustomButton from "@/components/CustomButton";
import CustomInput from "@/components/CustomInput";
import Table from "@/components/Table";
import {tableCols, tableData} from "@/mock/mock";
import LoadingFullPage from "@/components/LoadingFullPage";

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
    const [loading, setLoading] = useState<boolean>(false);

    const [walletData, setWalletData] = useState<InterfaceWalletData>({
        user: 0,
        name: "",
        objective: "",
        intenFixIncPercent: "",
        intenStockPercent: "",
        intenFilPercent: ""
    });

    // TODO - Implement the function to handle the form submit
    function handleDataSubmit() {
        if (isWalletDataValid()) {
            console.log('Will work');
        } else {
            console.error("Invalid data");
        }
    }

    function isWalletDataValid(): boolean {
        try {
            if (walletData.intenFixIncPercent.match(/[a-z]/i) || walletData.intenStockPercent.match(/[a-z]/i) || walletData.intenFilPercent.match(/[a-z]/i)) {
                alert("The percentages must be numbers");
                // Display all
                console.log(walletData);
                return false;
            }
            const sum: number = parseFloat(walletData.intenFixIncPercent) + parseFloat(walletData.intenStockPercent) + parseFloat(walletData.intenFilPercent);
            if (sum !== 100) {
                alert("The sum of the percentages must be 100");
                // Display all
                console.log(walletData);
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

    if (loading) {
        return <LoadingFullPage />;
    }

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className={"grid grid-cols-12 gap-4"}>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Wallet Name"} value={walletData.name}
                                     onChange={(e) => setWalletData({...walletData, name: e.target.value})}
                                     type={'text'} name={'name'} id={'name'}/>
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Wallet Objective"} value={walletData.objective}
                                     onChange={(e) => setWalletData({...walletData, objective: e.target.value})}
                                     type={'text'} name={'objective'} id={'objective'}/>
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput placeholder={"Fixed Income %"} value={walletData.intenFixIncPercent}
                                     onChange={(e) => setWalletData({
                                         ...walletData,
                                         intenFixIncPercent: e.target.value
                                     })}
                                     type={'number'} name={'intenFixIncPercent'} id={'intenFixIncPercent'}/>
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput placeholder={"Stock %"} value={walletData.intenStockPercent}
                                     onChange={(e) => setWalletData({...walletData, intenStockPercent: e.target.value})}
                                     type={'number'} name={'intenStockPercent'} id={'intenStockPercent'}/>
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput placeholder={"Fii %"} value={walletData.intenFilPercent}
                                     onChange={(e) => setWalletData({...walletData, intenFilPercent: e.target.value})}
                                     type={'number'} name={'intenFilPercent'} id={'intenFilPercent'}/>
                    </div>
                    <div className={"col-span-12"}>
                        <CustomButton type={"button"} onClick={handleDataSubmit}>
                            <span>Create Wallet</span>
                        </CustomButton>
                    </div>
                </div>
            </Card>
            <Card colspan={12} rowspan={1}>
                {/*Expect error below if using a button on one of the rows*/}
                <Table columns={tableCols} data={tableData} />
            </Card>
        </>
    );
}
