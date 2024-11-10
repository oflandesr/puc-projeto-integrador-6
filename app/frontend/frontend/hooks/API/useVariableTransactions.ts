import { useState } from "react";
import usePost from "@/hooks/Api/usePost";
import { AddVariableIncomeTransaction, VariableIncomeTransaction } from "@/config/interfaces";
import { isAddVariableIncomeTransactionValid } from "@/config/helpers";
import { useUser } from "@/userContext";

export function useVariableTransaction(walletId: string) {
    const { getUserData, getUserPassword } = useUser();
    const { data: postResponse, error: postError, loading: postLoading, postData: postTransaction } = usePost<VariableIncomeTransaction, AddVariableIncomeTransaction>();
    const [parentRefresh, setParentRefresh] = useState(false);

    async function handleTransaction(transaction: AddVariableIncomeTransaction) {
        if (!isAddVariableIncomeTransactionValid(transaction)) {
            alert("Invalid data");
            return;
        }
        
        const url = `/wallet/${walletId}/tvariable`;
        const headers = {
            username: getUserData().login,
            password: getUserPassword(),
        };

        await postTransaction(url, transaction, headers);
        setParentRefresh(!parentRefresh);
    }

    return {
        handleTransaction,
        postError,
        postLoading,
        parentRefresh,
    };
}
