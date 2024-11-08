"use client";

import { Ticker } from "@/config/interfaces";
import React, { useEffect, useState } from "react";

interface HeaderCardProps {
    title: string;
    value: string | number;
}

export default function HeaderCard({ title, value }: HeaderCardProps) {
    return (
        <div className="col-span-1 shadow  bg-white rounded-lg">
            <div className="grid grid-cols-1 text-center">
                <div className="col-span-1 font-semibold p-2 text-white bg-primary-600 border-b-1 border-black rounded-t-lg">
                    {title}
                </div>
                <div className="col-span-1 text-2xl font-bold p-4 text-black">
                    {value}
                </div>
            </div>
        </div>
    );

}
