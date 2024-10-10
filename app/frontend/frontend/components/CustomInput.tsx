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
        <div className="relative">
            <input
                type={type}
                name={name}
                id={id}
                value={value}
                onChange={onChange}
                className="block px-2.5 pb-2.5 pt-4 w-full text-sm text-gray-900 bg-transparent rounded-lg border border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                placeholder=" "
                autoComplete="off"
            />
            <label
                htmlFor={id}
                className={`absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform 
                    ${value ? '-translate-y-4 scale-75 top-2 z-10' : 'top-1/2 -translate-y-1/2 scale-100'} 
                    origin-[0] bg-white dark:bg-gray-900 px-2 
                    peer-focus:text-blue-600 peer-focus:dark:text-blue-500 
                    ${value ? '' : 'peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2'} 
                    cursor-text`}
            >
                {placeholder}
            </label>
        </div>
    );
};

export default CustomInput;
