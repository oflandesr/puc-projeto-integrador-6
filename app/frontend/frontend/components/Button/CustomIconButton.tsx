import React from 'react';
import { Bars } from "react-loading-icons";

interface CustomIconButtonProps {
    type?: 'button' | 'submit' | 'reset';
    onClick?: () => void;
    className?: string;
    icon: React.ReactNode; // Prop for the icon
    disabled?: boolean;
    loading?: boolean;
}

const CustomIconButton: React.FC<CustomIconButtonProps> = ({
    type = 'button',
    onClick,
    icon,
    className = '',
    disabled = false,
    loading = false
}) => {
    return (
        <button
            type={type}
            onClick={onClick}
            disabled={disabled || loading}
            className={`text-gray-500
                hover:bg-gray-100
                focus:ring-4 focus:outline-none
                font-medium rounded-full text-sm p-2 text-center 
                inline-flex items-center
                ${className}`}
        >
            {loading ? (
                <Bars className="w-5 h-5" fill={'currentColor'} /> // Change fill to currentColor for better color management
            ) : (
                icon // Render the icon prop here
            )}
        </button>
    );
};

export default CustomIconButton;
