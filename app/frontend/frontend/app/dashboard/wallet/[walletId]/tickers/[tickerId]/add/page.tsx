"use client";

import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import useFetchTicker from "@/hooks/useFetchTicker";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import { AddVariableIncomeTransaction, VariableIncomeTransaction } from "@/config/interfaces";
import CustomInput from "@/components/Layout/CustomInput";
import CustomDropdownV2 from "@/components/Button/CustomDropdownV2";
import { isAddVariableIncomeTransactionValid, isBuyOrSaleOptions } from "@/config/helpers";
import CustomButton from "@/components/Button/CustomButton";
import usePost from "@/hooks/API/usePost";
import VariableTransactionsTable from "@/components/Table/VariableTransactions/VariableTransactionsTable";
import { useUser } from "@/userContext";

export default function Home() {
    const router = useRouter();
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId, tickerId } = useParams() as { walletId: string; tickerId: string };
    const [parentRefresh, setParentRefresh] = useState<boolean>(false);

    const {
        ticker: getTicker,
        loading: loadingTicker,
        error: errorTicker
    } = useFetchTicker(tickerId);

    const {
        data: postResponse,
        error: postError,
        loading: postLoading,
        postData: postTransaction
    } = usePost<VariableIncomeTransaction, AddVariableIncomeTransaction>();

    const [variableIncomeParams, setVariableIncomeParams] = useState<AddVariableIncomeTransaction>({
        ticker: tickerId,
        buyOrSale: 0, // 0 for Buy, 1 for Sale
        date: new Date().toISOString().split('T')[0],
        amount: 0,
        price: 0
    });

    async function handleAddVariableTransaction(): Promise<void> {
        try {
            console.log(variableIncomeParams);
            if (!isAddVariableIncomeTransactionValid(variableIncomeParams)) {
                alert("Invalid data");
                return;
            }
            const url = `/wallet/${walletId}/tvariable`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await postTransaction(url, variableIncomeParams, headers);
            setParentRefresh(!parentRefresh);
        } catch (e) {
            console.error(e);
        }
    }

    if (loadingTicker) return <LoadingFullPage />;
    if (errorTicker) return <Card colspan={12} rowspan={1}>Ticker not found</Card>;

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className={"grid grid-cols-12 gap-4"}>
                    <div className={"col-span-12"}>
                        <div className={"flex justify-between"}>
                            <h1 className={"text-2xl font-semibold"}>Add Variable Transaction for <span className={"text-blue-500"}>{tickerId}</span></h1>
                        </div>
                    </div>
                    <div className={"col-span-12 hidden"}>
                        <CustomInput
                            placeholder={"Ticker"}
                            value={variableIncomeParams.ticker}
                            onChange={(e) => setVariableIncomeParams({ ...variableIncomeParams, ticker: e.target.value })}
                            type={'text'}
                            name={'institution'}
                            id={'institution'}
                            disabled={true}
                        />
                    </div>
                    <div className={"col-span-12"}>
                        <CustomDropdownV2
                            options={isBuyOrSaleOptions}
                            label={null}
                            placeholder={"Buy or Sell"}
                            selected={variableIncomeParams.buyOrSale.toString()}
                            onChange={(e) => setVariableIncomeParams({ ...variableIncomeParams, buyOrSale: parseInt(e.target.value) })}
                        />
                    </div>
                    <div className={"col-span-12"}>
                        <CustomInput
                            placeholder={"Start Date"}
                            value={variableIncomeParams.date}
                            onChange={(e) => setVariableIncomeParams({ ...variableIncomeParams, date: new Date(e.target.value).toISOString().split('T')[0] })}
                            type={'date'}
                            name={'startDate'}
                            id={'startDate'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput
                            placeholder={"Amount"}
                            value={variableIncomeParams.amount}
                            onChange={(e) => setVariableIncomeParams({ ...variableIncomeParams, amount: parseFloat(e.target.value) })}
                            type={'number'}
                            name={'amount'}
                            id={'amount'}
                        />
                    </div>
                    <div className={"col-span-6"}>
                        <CustomInput
                            placeholder={"Price"}
                            value={variableIncomeParams.price}
                            onChange={(e) => setVariableIncomeParams({ ...variableIncomeParams, price: parseFloat(e.target.value) })}
                            type={'number'}
                            name={'price'}
                            id={'price'}
                        />
                    </div>
                    <div className={"col-span-12"}>
                        <CustomButton type="button" onClick={handleAddVariableTransaction} loading={postLoading}>
                            Add New Transaction
                        </CustomButton>
                    </div>
                </div>
            </Card>
            {postError && <Card colspan={12} rowspan={1}>{postError}</Card>}
            <Card colspan={12} rowspan={1}>
                <VariableTransactionsTable customColSpan={12} customRowSpan={1} parentRefresh={parentRefresh} />
            </Card>
        </>
    );
}
