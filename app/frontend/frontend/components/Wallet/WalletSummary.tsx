"use client";

import { Wallet } from "@/config/interfaces";
import { useUser } from "@/userContext";
import { useParams } from "next/navigation";
import { useRouter } from "next/navigation";
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
            title="Objetivo de alocação"
            height={250}
            onclick={() => {
                router.push(`/dashboard/wallet/${walletId}/edit`);
            }}
            labels={["Renda Fixa Obj", "Ações Obj", "FII Obj"]}
            data={series}
            btnText="Editar"
        />
    );
};

export default WalletSummary;