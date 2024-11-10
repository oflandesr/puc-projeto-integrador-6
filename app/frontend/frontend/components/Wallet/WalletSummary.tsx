"use client";

import { Wallet } from "@/config/interfaces";
import { useUser } from "@/userContext";
import { useParams } from "next/navigation";
import { useRouter } from "next/navigation";
import Card from "../Layout/Card";
import Circle from "../Charts/Circle/Circle";

const WalletSummary = ({ walletData }: { walletData: Wallet }) => {
    const router = useRouter();
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();

    const series = [
        parseInt(walletData.intenFixIncPercent ?? "0"),
        parseInt(walletData.intenStockPercent ?? "0"),
        parseInt(walletData.intenFilPercent ?? "0"),
    ];

    return (
        <Circle
            title=""
            height={250}
            onclick={() => {
                router.push(`/dashboard/wallet/${walletId}/edit`);
            }}
            labels={["Fixed Income", "Stock", "FII"]}
            data={series}
            btnText="Editar"
        />
    );
};

export default WalletSummary;