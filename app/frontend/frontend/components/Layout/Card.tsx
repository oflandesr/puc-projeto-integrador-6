"use client";

import React  from "react";

interface LineProps {
    colspan: number;
    rowspan: number;
    children: React.ReactNode;
}

export default function Card ({ colspan, rowspan, children }: LineProps) {

    return (
        <div className={`col-span-${colspan} row-span-${rowspan} bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6`}>
                {children}
        </div>
    );
}
