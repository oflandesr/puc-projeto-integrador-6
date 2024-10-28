import React from 'react';
import {Bars} from "react-loading-icons";

interface CustomButtonProps {
    type?: 'button' | 'submit' | 'reset';
    onClick?: () => void;
    className?: string;
    children: React.ReactNode;
    disabled?: boolean;
    loading?: boolean;
}

const CustomButton: React.FC<CustomButtonProps> = ({
   type = 'button',
   onClick,
   children,
    className = '',
    disabled = false,
    loading = false
}) => {
    return (
        <button
            type={type}
            onClick={onClick}
            disabled={disabled || loading}
            className={`w-full justify-center items-center inline-flex text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800 ${className}`}
        >
            { //. if loading show loading if not show a icon with the button
                loading ? (
                    <Bars className="w-5 h-5" fill={'white'} />
                ) : (
                    children
                )
            }
        </button>
    );
};

export default CustomButton;
