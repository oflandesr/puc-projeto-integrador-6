"use client";

import React, { use, useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import ErrorFullPage from "@/components/Layout/ErrorFullPage";
import useGet from "@/hooks/API/useGet";
import { FixedInvestmentInterest } from "@/config/interfaces";
import { useUser } from "@/userContext";
import useLazyGet from "@/hooks/API/useLazyGet";
import { tableColsFixedInvestmentInterest } from "@/mock/mock";
import Table from "@/components/Table/Table";

export default function Home() {
    const router = useRouter();
    const { walletId } = useParams() as { walletId: string; };
    const { getUserData, getUserPassword } = useUser();

    const {
        data: fixedInvestmentInterestsData,
        loading: fixedInvestmentInterestsLoading,
        error: fixedInvestmentInterestsError,
        fetchData: fetchFixedInvestmentInterests,
    } = useLazyGet<FixedInvestmentInterest[]>();

    useEffect(() => {
        try {
            fetchFixedInvestmentInterests(
                `/wallet/${walletId}/tfixed/variation/institution`,
                { username: getUserData().login, password: getUserPassword() }
            );
        } catch (error) {
            console.error(error);
        }
    }, []);

    if (fixedInvestmentInterestsLoading) return <LoadingFullPage />;
    if (fixedInvestmentInterestsError) return <ErrorFullPage error={fixedInvestmentInterestsError} />;

    return (
        <>
        <Card colspan={12} rowspan={1}>
            <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold">
                    Investimentos Fixos - Rendimento por Instituição
                </h2>
            </div>
        </Card>
        <Card colspan={12} rowspan={1}>
            {fixedInvestmentInterestsData && (
                <Table columns={tableColsFixedInvestmentInterest} data={fixedInvestmentInterestsData} />
            )}
        </Card>
        </>
    );
}
