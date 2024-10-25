"use client";

import React, { useEffect } from "react";
import { Wallet } from "@/config/interfaces";
import CustomButton from "@/components/CustomButton";
import { FaTrash} from "react-icons/fa";
import useDelete from "@/hooks/useDelete";
import { useUser } from "@/userContext";
import { useRouter } from "next/navigation";

interface WalletPageProps {
    walletData: Wallet | null;
}

export default function WalletHeader({ walletData }: WalletPageProps) {

    const router = useRouter();
    const {getUserData, userId, getUserPassword} = useUser();
    const {
        data: deleteWalletResponse,
        error: deleteWalletError,
        loading: deleteWalletLoading,
        deleteData: deleteWalletData
    } = useDelete<any>();

    async function handleDeleteWallet() { // todo: check this route, maybe it is not working
        try {
            if (!walletData) {
                console.error("No wallet data provided");
                return;
            }
            const url = `/wallet/${walletData?.id}`;
            const headers = {
                username: getUserData().login,
                password: getUserPassword()
            };
            await deleteWalletData(url, headers);
            // router.push("/dashboard");
        } catch (e) {
            console.error(e);
        }
    }

    // debug wallet response
    useEffect(() => {
        console.log(deleteWalletResponse);
    }, [deleteWalletResponse]);

    if (!walletData) {
        return (
            <div className="flex justify-center items-center h-16">
                <h1 className="text-2xl font-semibold">
                    Error loading wallet data
                </h1>
            </div>
        );
    }

    return (
        <div className="flex flex-col items-center h-16">
            <h1 className="text-2xl font-semibold mb-2">
                {walletData?.name}
            </h1>
            <CustomButton
                type={"button"}
                onClick={() => handleDeleteWallet()}
                className={"bg-transparent hover:bg-red-200 text-red-400 flex items-center hover:bg-transparent w-16 h-8"}
            >
                <FaTrash className="w-4 h-4 text-gray-400" /> {/* Ícone de lixeira */}
            </CustomButton>
        </div>
    );
}
