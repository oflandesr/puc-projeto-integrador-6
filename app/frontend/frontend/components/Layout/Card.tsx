"use client";

import React  from "react";

interface LineProps {
    colspan: number;
    rowspan: number;
    children: React.ReactNode;
    className?: string;
    key?: string;
}

export default function Card ({ colspan, rowspan, children, className, key }: LineProps) {

    return (
        <div className={`sm:col-span-${colspan} sm:row-span-${rowspan} bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6` + (className ? ` ${className}` : '') + ` col-span-12 row-span-1`} 
            key={key ? key : ''}
        >
                {children}
        </div>
    );
}
