"use client";

import React, { useState } from "react";
import {useUser} from "@/userContext";
import Card from "@/components/Layout/Card";
import CustomButton from "@/components/Button/CustomButton";
import CustomInput from "@/components/Layout/CustomInput";
import WalletsTable from "@/components/Table/Wallet/WalletTable";
import {CreateWallet, Wallet} from "@/config/interfaces";
import usePost from "@/hooks/API/usePost";
import ErrorCard from "@/components/Layout/ErrorCard";


export default function Home() {
    const {getUserData, userId, getUserPassword} = useUser();
    const [parentRefresh, setParentRefresh] = useState<boolean>(false);

    const {
        error : createWalletError,
        loading : createWalletLoading,
        postData : createWalletResponse
    } = usePost<Wallet, CreateWallet>();

    const [walletData, setWalletData] = useState<CreateWallet>({
        user: parseInt(userId as string),
        name: "",
        objective: "",
        intenFixIncPercent: 0,
        intenStockPercent: 0,
        intenFilPercent: 0
    });

    async function handleDataSubmit() {
        try {
            if (isWalletDataValid()) {
                const url = "/wallet";
                const auth = { username: getUserData().login, password: getUserPassword() };
                await createWalletResponse(url, walletData, auth);
                if (!createWalletError) {
                    setWalletData({
                        user: parseInt(userId as string),
                        name: "",
                        objective: "",
                        intenFixIncPercent: 0,
                        intenStockPercent: 0,
                        intenFilPercent: 0
                    });
                    setParentRefresh(!parentRefresh);
                }
            } else {
                alert("Invalid wallet data");
                new Error("Invalid wallet data");
            }
        } catch (e) {
            console.error(e);
        }
    }

    function isWalletDataValid(): boolean {
        try {
            const sum: number = walletData.intenFixIncPercent + walletData.intenStockPercent + walletData.intenFilPercent;
            if (sum !== 100) {
                alert("The sum of the percentages must be 100");
                return false;
            }
            return walletData.name !== "" && walletData.objective !== "" && sum === 100;
        } catch (e) {
            console.error(e);
            return false;
        }
    }

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className={"grid grid-cols-12 gap-4"}>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Nome Carteira"} value={walletData.name}
                                     onChange={(e) => setWalletData({...walletData, name: e.target.value})}
                                     type={'text'} name={'name'} id={'name'}/>
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Objetivo Carteira"} value={walletData.objective}
                                     onChange={(e) => setWalletData({...walletData, objective: e.target.value})}
                                     type={'text'} name={'objective'} id={'objective'}/>
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput
                            placeholder={"Renda Fixa %"}
                            value={walletData.intenFixIncPercent || ""} // Use an empty string if the value is falsy
                            onChange={(e) => {
                                const value = e.target.value;
                                setWalletData({
                                    ...walletData,
                                    intenFixIncPercent: value === "" ? 0 : parseInt(value) // Handle empty string case
                                });
                            }}
                            type={'number'}
                            name={'intenFixIncPercent'}
                            id={'intenFixIncPercent'}
                        />
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput
                            placeholder={"Ações %"}
                            value={walletData.intenStockPercent || ""}
                            onChange={(e) => {
                                const value = e.target.value;
                                setWalletData({
                                    ...walletData,
                                    intenStockPercent: value === "" ? 0 : parseInt(value)
                                });
                            }}
                            type={'number'}
                            name={'intenStockPercent'}
                            id={'intenStockPercent'}
                        />
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput
                            placeholder={"Fundos %"}
                            value={walletData.intenFilPercent || ""}
                            onChange={(e) => {
                                const value = e.target.value;
                                setWalletData({
                                    ...walletData,
                                    intenFilPercent: value === "" ? 0 : parseInt(value)
                                });
                            }}
                            type={'number'}
                            name={'intenFilPercent'}
                            id={'intenFilPercent'}
                        />
                    </div>
                    <div className={"col-span-12"}>
                        <CustomButton type={"button"} onClick={handleDataSubmit} loading={createWalletLoading}>
                            <span>Criar Carteira</span>
                        </CustomButton>
                    </div>
                </div>
            </Card>
            <ErrorCard colspan={12} rowspan={1} error={createWalletError ? 'Error Creating New Wallet' : null}/>
            <Card colspan={12} rowspan={1}>
                <WalletsTable customColSpan={12} customRowSpan={1} parentRefresh={parentRefresh}/>
            </Card>
        </>
    );
}
