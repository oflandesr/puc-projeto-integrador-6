"use client";

import React from "react";
import FixedChart from "@/components/Charts/Fixed/FixedChart";
import Header from "@/components/Layout/Header";

export default function Home() {
    return (
        <>
            <Header title="Investimentos Fixos - Gráficos" />
            <FixedChart />
        </>
    );
}