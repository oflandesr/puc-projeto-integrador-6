import React from 'react';

interface CustomInputProps {
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    placeholder?: string;
    type: string;
    name: string;
    id: string;
}

const CustomInput: React.FC<CustomInputProps> = ({
 value,
 onChange,
 placeholder = "Enter value...",
 type,
 name,
 id,
}) => {
    return (
        <div>
            <label
                htmlFor={id}
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
            >
                {placeholder}
            </label>
            <input
                type={type}
                name={name}
                id={id}
                value={value}
                onChange={onChange}
                className="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder={placeholder}
            />
        </div>
    );
};

export default CustomInput;
