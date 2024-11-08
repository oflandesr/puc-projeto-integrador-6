import React from "react";
import Card from "@/components/Layout/Card";
import { Ticker } from "@/config/interfaces";
import CustomButton from "@/components/Button/CustomButton";
import { useRouter } from "next/navigation";

interface TickerActionsProps {
    walletId: string;
    tickerId: string;
}

function TickerActions({ walletId, tickerId }: TickerActionsProps) {
    const router = useRouter();
    
    return (
        <Card colspan={12} rowspan={1}>
            <CustomButton onClick={() => router.push(`/dashboard/wallet/${walletId}/tickers/${tickerId}/add`)}>
                Add To Wallet
            </CustomButton>
        </Card>
    );
}
export default TickerActions