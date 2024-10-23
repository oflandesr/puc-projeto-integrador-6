"use client";

import React from "react";
import { Wallet } from "@/config/interfaces";
import CustomButton from "@/components/CustomButton";
import { FaTrash} from "react-icons/fa";

interface WalletPageProps {
    walletData: Wallet | null;
}

export default function WalletHeader({ walletData }: WalletPageProps) {
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
                onClick={() => alert("Delete")}
                className={"bg-transparent hover:bg-gray-200 text-red-400 flex items-center"}
            >
                <FaTrash className="ml-1 w-4 h-4 text-gray-400" /> {/* Ícone de lixeira */}
            </CustomButton>
        </div>
    );
}
