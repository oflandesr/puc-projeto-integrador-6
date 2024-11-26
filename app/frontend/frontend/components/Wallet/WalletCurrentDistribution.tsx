"use client";

import { FilterWalletDistribution, Wallet } from "@/config/interfaces";
import { useUser } from "@/userContext";
import { useParams } from "next/navigation";
import { useRouter } from "next/navigation";
import Circle from "../Charts/Circle/Circle";
import { useEffect, useState } from "react";
import { filterWalletDistribution } from "@/config/helpers";

interface Series {
    value: number[];
}

const WalletCurrentDistribution = ({ walletData }: { walletData: Wallet }) => {
    const router = useRouter();
    const {getUserData, userId, getUserPassword} = useUser();
    const { walletId } = useParams();

    const [series, setSeries] = useState<Series>({
        value: [0, 0, 0]
    });

    // SET SERIES 
    useEffect(() => {
        let res : FilterWalletDistribution = filterWalletDistribution(walletData);
        setSeries({
            value: [
                parseInt(res.totalFixed ?? "0"),
                parseInt(res.totalStock ?? "0"),
                parseInt(res.totalFII ?? "0"),
            ]
        });
    }, [walletData]);


    return (
        <Circle
            title="Alocação atual da carteira"
            height={250}
            labels={["Renda Fixa Atual", "Ações Atuais", "FII Atual"]}
            data={series.value}
        />
    );
};
export default WalletCurrentDistribution;