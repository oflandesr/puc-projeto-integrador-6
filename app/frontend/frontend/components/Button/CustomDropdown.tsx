import React from 'react';

interface CustomDropdownProps {
    label: string | null;
    options: string[];
    selected: string;
    onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
    placeholder?: string; // Added placeholder prop
    disabled?: boolean; // Added disabled prop
    error?: string; // Added error prop
    className?: string; // Added className prop for custom styling
    renderOption?: (option: string) => React.ReactNode; // Added renderOption prop
}

const CustomDropdown: React.FC<CustomDropdownProps> = ({
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
                disabled={disabled} // Enable/disable based on prop
            >
                {placeholder && <option value="" disabled>{placeholder}</option>}
                {options.map((option, index) => (
                    <option key={index} value={option}>
                        {renderOption ? renderOption(option) : option} {/* Custom option rendering */}
                    </option>
                ))}
            </select>
            {error && <p className="mt-1 text-sm text-red-600">{error}</p>} {/* Error message */}
        </div>
    );
};

export default CustomDropdown;
