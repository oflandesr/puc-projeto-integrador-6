import React from "react";
import Card from "@/components/Layout/Card";
import { Ticker } from "@/config/interfaces";

interface TickerHeaderProps {
    ticker: Ticker;
}

function TickerHeader({ ticker }: TickerHeaderProps) {
    return (
        <Card colspan={12} rowspan={1}>
            <div className="flex justify-between items-center flex-row">
                <div className="flex flex-col">
                    <h5 className="text-3xl font-bold text-primary-600">{ticker.id}</h5>
                    <p className="text-gray-500 text-sm">{ticker.shortName}</p>
                </div>
            </div>
        </Card>
    );
}

export default TickerHeader;