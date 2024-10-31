"use client";

import React, { useState } from "react";
import {useUser} from "@/userContext"
import {useParams} from "next/navigation";
import Card from "@/components/Layout/Card";
import {AddFixedIncomeTransaction, AddFixedIncomeTransactionResponse} from "@/config/interfaces";
import CustomInput from "@/components/Layout/CustomInput";
import CustomButton from "@/components/Button/CustomButton";
import CustomDropdown from "@/components/Button/CustomDropdown";
import FixedTransactionsTable from "@/components/Table/FixedTransactions/FixedTransactionsTable";
import usePost from "@/hooks/API/usePost";
import { indexesOptions } from "@/config/helpers";

export default function Home() {

    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();

    const [parentRefresh, setParentRefresh] = useState<boolean>(false);
    const [currentWalletData, setCurrentWalletData] = useState<AddFixedIncomeTransaction>({
        institution: "",
        type: indexesOptions[0],
        value: 0,
        indexName: "",
        taxValue: 0,
        startDate: new Date().toISOString().split('T')[0],
        endDate: new Date().toISOString().split('T')[0],
    });

    const {
        error: postError,
        loading: postLoading,
        postData: postTransaction,
        data: postResponse,
    } = usePost<AddFixedIncomeTransactionResponse, AddFixedIncomeTransaction>();

    async function addFixedIncomeTransaction() {
        if (!isDataValid(currentWalletData)) {
            alert("Invalid data");
            return;
        }
        try {
            const url = `/wallet/${walletId}/tfixed`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            console.log(currentWalletData);
            await postTransaction(url, currentWalletData, headers);
            setParentRefresh(!parentRefresh);
        } catch (error) {
            console.log(error);
        }
    }

    function isDataValid( data: AddFixedIncomeTransaction ): boolean {
        if (data.startDate.length !== 10 || data.endDate.length !== 10) {
            alert("Invalid date 1");
            return false;
        }
        if (new Date(data.startDate) > new Date(data.endDate)) {
            alert("Invalid date 3");
            return false;
        }
        return data.institution !== "" && data.type !== "" && data.value > 0 && data.indexName !== "" && data.taxValue >= 0 && data.startDate !== "" && data.endDate !== "";
    }

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className={"grid grid-cols-12 gap-4"}>
                    <div className={"col-span-12"}>
                        <CustomInput 
                            placeholder={"Apelido"}
                            value={currentWalletData.institution}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, institution: e.target.value})}
                            type={'text'} 
                            name={'institution'} 
                            id={'institution'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput 
                            placeholder={"Index Name"}
                            value={currentWalletData.indexName}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, indexName: e.target.value})}
                            type={'text'} 
                            name={'indexName'} 
                            id={'indexName'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput 
                            placeholder={"Value"}
                            value={currentWalletData.value}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, value: parseFloat(e.target.value)})}
                            type={'number'} 
                            name={'value'} 
                            id={'value'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomDropdown 
                            label={null}
                            placeholder={"Type"}
                            selected={currentWalletData.type}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, type: e.target.value})}
                            options={Object.values(indexesOptions)}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput 
                            placeholder={"Tax Value"}
                            value={currentWalletData.taxValue}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, taxValue: parseFloat(e.target.value)})}
                            type={'number'} 
                            name={'taxValue'} 
                            id={'taxValue'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput 
                            placeholder={"Start Date"}
                            value={currentWalletData.startDate}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, startDate: new Date(e.target.value).toISOString().split('T')[0]})}
                            type={'date'} 
                            name={'startDate'} 
                            id={'startDate'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput 
                            placeholder={"End Date"}
                            value={currentWalletData.endDate}
                            onChange={(e) => setCurrentWalletData({...currentWalletData, endDate: new Date(e.target.value).toISOString().split('T')[0]})}
                            type={'date'} 
                            name={'endDate'} 
                            id={'endDate'}
                        />
                    </div>
                    <div className={"col-span-12"}>
                        <CustomButton type="button" onClick={addFixedIncomeTransaction} loading={postLoading}>
                            Add New Transaction
                        </CustomButton>
                    </div>
                </div>
            </Card>
            { postError && (
                <Card colspan={12} rowspan={1}>
                    <p className="text-red-500 font-bold">
                        {postError}
                    </p>
                </Card>
            )}
            <Card colspan={12} rowspan={1}>
                <FixedTransactionsTable customColSpan={12} customRowSpan={1} parentRefresh={parentRefresh} />
            </Card>
        </>
    );
}