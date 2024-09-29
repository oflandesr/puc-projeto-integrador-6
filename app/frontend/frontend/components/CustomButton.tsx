import React from 'react';

interface CustomButtonProps {
    type?: 'button' | 'submit' | 'reset';
    onClick?: () => void;
    className?: string;
    children: React.ReactNode;
}

const CustomButton: React.FC<CustomButtonProps> = ({
   type = 'button',
   onClick,
   className = '',
   children,
}) => {
    return (
        <button
            type={type}
            onClick={onClick}
            className={`w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800 ${className}`}
        >
            {children}
        </button>
    );
};

export default CustomButton;
