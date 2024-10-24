"use client";

import React, {useEffect, useState} from "react";
import {useUser} from "@/userContext"
import {useParams} from "next/navigation";
import Card from "@/components/Layout/Card";
import {PatchWalletResponse, Wallet} from "@/config/interfaces";
import useLazyGet from "@/hooks/useLazyGet";
import LoadingFullPage from "@/components/LoadingFullPage";
import WalletsTable from "@/components/Table/Wallet/WalletTable";
import usePatch from "@/hooks/usePatch";
import CustomInput from "@/components/CustomInput";
import CustomButton from "@/components/CustomButton";
import ErrorCard from "@/components/Layout/ErrorCard";

export default function Home() {
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();

    const [parentRefresh, setParentRefresh] = useState<boolean>(false);
    const [currentWalletData, setCurrentWalletData] = useState<PatchWalletResponse>({
        name: "",
        objective: "",
        intenFixIncPercent: "0",
        intenStockPercent: "0",
        intenFilPercent: "0",
    });

    const {
        error : walletError,
        loading : loadingWallet,
        data : walletData,
        fetchData : fetchWalletData
    } = useLazyGet<Wallet>();

    const {
        error : patchWalletError,
        loading : patchWalletLoading,
        patchData : patchWallet
    } = usePatch<Wallet>();

    async function handleGetWalletData() {
        try {
            const url = `/wallet/${walletId}`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await fetchWalletData(url, headers);
        } catch (e) {
            console.error(e);
        }
    }

    async function handleDataSubmit() {
        try {
            if (isWalletDataValid()) {
                const url = `/wallet/${walletId}`;
                const auth = { username: getUserData().login, password: getUserPassword() };
                await patchWallet(url, auth, currentWalletData);
                alert("Wallet updated successfully");
                setParentRefresh(!parentRefresh);
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
            // First check if the total sum of percentages is 100
            const sum: number = parseInt(currentWalletData.intenFixIncPercent) + parseInt(currentWalletData.intenStockPercent) + parseInt(currentWalletData.intenFilPercent);
            if (sum !== 100) {
                alert("The sum of the percentages must be 100");
                return false;
            }
            // Check if all fields are filled
            return currentWalletData.name !== "" && currentWalletData.objective !== "" && sum === 100;
        } catch (e) {
            console.error(e);
            return false;
        }
    }

    useEffect(() => {
        handleGetWalletData();
    }, [getUserData, userId]);

    useEffect(() => {
        try {
            if (walletData) {
                // Set the current wallet data
                setCurrentWalletData({
                    name: walletData.name,
                    objective: walletData.objective,
                    intenFixIncPercent: walletData.intenFixIncPercent,
                    intenStockPercent: walletData.intenStockPercent,
                    intenFilPercent: walletData.intenFilPercent,
                });
            }
        } catch (e) {
            console.error(e);
            alert("Error loading wallet data");
        }
    }, [walletData]);

    if (loadingWallet || patchWalletLoading) {
        return <LoadingFullPage />;
    }

    if (walletError || patchWalletError) {
        return (
            <Card colspan={12} rowspan={1}>
                <div>Error loading wallet</div>
            </Card>
        );
    }

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className={"grid grid-cols-12 gap-4"}>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Wallet Name"} value={currentWalletData.name}
                                     onChange={(e) => setCurrentWalletData({...currentWalletData, name: e.target.value})}
                                     type={'text'} name={'name'} id={'name'}/>
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput placeholder={"Wallet Objective"} value={currentWalletData.objective}
                                     onChange={(e) => setCurrentWalletData({...currentWalletData, objective: e.target.value})}
                                     type={'text'} name={'objective'} id={'objective'}/>
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput
                            placeholder={"Fixed Income %"}
                            value={currentWalletData.intenFixIncPercent || ""} // Use an empty string if the value is falsy
                            onChange={(e) => {
                                const value = e.target.value;
                                setCurrentWalletData({
                                    ...currentWalletData,
                                    intenFixIncPercent: value === "" ? "0" : value // Handle empty string case
                                });
                            }}
                            type={'number'}
                            name={'intenFixIncPercent'}
                            id={'intenFixIncPercent'}
                        />
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput
                            placeholder={"Stock %"}
                            value={currentWalletData.intenStockPercent || ""}
                            onChange={(e) => {
                                const value = e.target.value;
                                setCurrentWalletData({
                                    ...currentWalletData,
                                    intenStockPercent: value === "" ? "0" : value
                                });
                            }}
                            type={'number'}
                            name={'intenStockPercent'}
                            id={'intenStockPercent'}
                        />
                    </div>
                    <div className={"col-span-4"}>
                        <CustomInput
                            placeholder={"Fii %"}
                            value={currentWalletData.intenFilPercent || ""}
                            onChange={(e) => {
                                const value = e.target.value;
                                setCurrentWalletData({
                                    ...currentWalletData,
                                    intenFilPercent: value === "" ? "0" : value
                                });
                            }}
                            type={'number'}
                            name={'intenFilPercent'}
                            id={'intenFilPercent'}
                        />
                    </div>
                    <div className={"col-span-12"}>
                        <CustomButton type={"button"} onClick={handleDataSubmit} loading={patchWalletLoading}>
                            <span>Create Wallet</span>
                        </CustomButton>
                    </div>
                </div>
            </Card>
            <ErrorCard colspan={12} rowspan={1} error={patchWalletError ? 'Error Updating Wallet' : null}/>
            <Card colspan={12} rowspan={1}>
                <WalletsTable customColSpan={12} customRowSpan={1} parentRefresh={parentRefresh}/>
            </Card>
        </>
    );
}