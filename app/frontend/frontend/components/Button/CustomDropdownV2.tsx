import { CustomDropdownV2Options } from '@/config/interfaces';
import React from 'react';

interface CustomDropdownProps {
    label: string | null;
    options: CustomDropdownV2Options[]; // Updated type for options
    selected: string;
    onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
    placeholder?: string;
    disabled?: boolean;
    error?: string;
    className?: string;
    renderOption?: (option: CustomDropdownV2Options) => React.ReactNode;
}

const CustomDropdownV2: React.FC<CustomDropdownProps> = ({
    label,
    options,
    selected,
    onChange,
    placeholder,
    disabled = false,
    error,
    className = '',
    renderOption,
}) => {
    return (
        <div className="relative">
            {label && (
                <label 
                    htmlFor="custom-dropdown"
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                >
                    {label}
                </label>
            )}
            <select
                id="custom-dropdown"
                value={selected}
                onChange={onChange}
                className={`bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 ${className}`}
                aria-label={label || "Custom dropdown"}
                disabled={disabled}
            >
                {placeholder && <option value="" disabled>{placeholder}</option>}
                {options.map((option, index) => (
                    <option key={index} value={option.value}>
                        {renderOption ? renderOption(option) : option.name}
                    </option>
                ))}
            </select>
            {error && <p className="mt-1 text-sm text-red-600">{error}</p>}
        </div>
    );
};

export default CustomDropdownV2;
