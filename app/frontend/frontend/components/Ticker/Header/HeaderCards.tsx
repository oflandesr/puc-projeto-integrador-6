"use client";

import { Ticker } from "@/config/interfaces";
import React, { useEffect, useState } from "react";
import Card from "@/components/Layout/Card";
import HeaderCard from "./HeaderCard";

interface HeaderCardsProps {
    ticker: Ticker;
}

export default function HeaderCards({ ticker }: HeaderCardsProps) {

    if (!ticker) {
        return (
            <Card colspan={12} rowspan={1}>
                Ticker data not found
            </Card>
        );
    }

    return (
        <>
            <div className="col-span-12 row-span-1">
                {/* We need a bunch of little squares ( 4x ) */}
                <div className="grid grid-cols-4 gap-4">
                    <HeaderCard value={ticker.regularMarketOpen} title="Open" />
                    <HeaderCard value={ticker.regularMarketVolume} title="Volume" />
                    <HeaderCard value={ticker.regularMarketDayHigh} title="High" />
                    <HeaderCard value={ticker.regularMarketDayLow} title="Low" />
                </div>
            </div>
        </>
    );

}
