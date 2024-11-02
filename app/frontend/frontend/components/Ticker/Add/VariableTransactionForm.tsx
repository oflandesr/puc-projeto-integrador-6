import React, { useState } from "react";
import CustomInput from "@/components/Layout/CustomInput";
import CustomDropdownV2 from "@/components/Button/CustomDropdownV2";
import CustomButton from "@/components/Button/CustomButton";
import { AddVariableIncomeTransaction } from "@/config/interfaces";
import { isBuyOrSaleOptions } from "@/config/helpers";

interface VariableTransactionFormProps {
    tickerId: string;
    onSubmit: (transaction: AddVariableIncomeTransaction) => void;
    loading: boolean;
}

const VariableTransactionForm: React.FC<VariableTransactionFormProps> = ({ tickerId, onSubmit, loading }) => {
    const [variableIncomeParams, setVariableIncomeParams] = useState<AddVariableIncomeTransaction>({
        ticker: tickerId,
        buyOrSale: 0,
        date: new Date().toISOString().split('T')[0],
        amount: 0,
        price: 0,
    });

    const handleInputChange = (field: keyof AddVariableIncomeTransaction, value: any) => {
        setVariableIncomeParams(prev => ({ ...prev, [field]: value }));
    };

    return (
        <div className="grid grid-cols-12 gap-4">
            <CustomDropdownV2
                label={null}
                options={isBuyOrSaleOptions}
                placeholder={"Buy or Sell"}
                selected={variableIncomeParams.buyOrSale.toString()}
                onChange={(e) => handleInputChange("buyOrSale", parseInt(e.target.value))}
            />
            <CustomInput
                placeholder={"Date"}
                value={variableIncomeParams.date}
                onChange={(e) => handleInputChange("date", e.target.value)}
                type="date"
                name="date"
                id="date"
            />
            <CustomInput
                placeholder={"Amount"}
                value={variableIncomeParams.amount}
                onChange={(e) => handleInputChange("amount", parseFloat(e.target.value))}
                type="number"
                name="amount"
                id="amount"            
            />
            <CustomInput
                placeholder={"Price"}
                value={variableIncomeParams.price}
                onChange={(e) => handleInputChange("price", parseFloat(e.target.value))}
                type="number"
                name="price"
                id="price"
            />
            <CustomButton type="button" onClick={() => onSubmit(variableIncomeParams)} loading={loading}>
                Add New Transaction
            </CustomButton>
        </div>
    );
};

export default VariableTransactionForm;
