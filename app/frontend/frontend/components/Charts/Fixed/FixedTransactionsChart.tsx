"use client";

import React, {useEffect, useState} from "react";
import {useUser} from "@/userContext"
import {useParams, useRouter} from "next/navigation";
import Card from "@/components/Layout/Card";
import { extraYAxis, Index } from "@/config/interfaces";
import useLazyGet from "@/hooks/API/useLazyGet";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import Line from "../Line/Line";

export default function FixedTransactionChart() {

    const router = useRouter();
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();

    const [cdi, setCdi] = useState<number[]>([]);
    const [ipca, setIpca] = useState<extraYAxis>({ name: "IPCA", extraYAxis: [] });
    const [selic, setSelic] = useState<extraYAxis>({ name: "Selic", extraYAxis: [] });

    const {
        error : indexError,
        loading : indexLoading,
        data : indexData,
        fetchData : fetchIndexData
    } = useLazyGet<Index[]>();

    async function handleGetIndexData() {
        try {
            const url = `/index`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword(),
            };
            await fetchIndexData(url, headers);
        } catch (e) {
            console.error(e);
        }
    }

    useEffect(() => {
        handleGetIndexData();
    }, [getUserData, userId]);

    useEffect(() => {
        try {
            if (!indexData) {
                return;
            }

            indexData?.forEach((index) => {
                setCdi((prev) => [...prev, index.cdi ?? 0]);
                setIpca((prev) => {
                    if (index.ipca) {
                        return { name: "IPCA", extraYAxis: [...prev.extraYAxis, index.ipca] };
                    }
                    return prev;
                });
                setSelic((prev) => {
                    if (index.selic) {
                        return { name: "Selic", extraYAxis: [...prev.extraYAxis, index.selic] };
                    }
                    return prev;
                });
            });
        } catch (e) {
            console.error(e);
        }
    }, [indexData]);

    if (indexLoading) {
        return <LoadingFullPage />;
    }

    if (indexError) {
        return (
            <Card colspan={12} rowspan={1}>
                <div>Error loading index</div>
            </Card>
        );
    }

    if (!indexData) {
        return (
            <>
                <div>No index data</div>
            </>
        );
    }

    return (
        <>
            <Line 
                title="Price"
                seriesName="CDI" 
                height={400} 
                isBtnActive={false}
                onClick={() => {}}
                showLabel={false}
                yAxis={cdi}
                xAxis={indexData.map((p) => {
                    // format from yyyy-mm-dd to dd/mm/yyyy
                    const date = new Date(p.date);
                    return date.toLocaleDateString();
                })}
                extraYAxis={[ipca, selic]}
            />
        </>
    );
}