"use client";

import getChartOptions from "./getChartOptions";
import React from "react";
import Chart from "react-apexcharts";
import CustomButton from "@/components/Button/CustomButton";
import { extraYAxis } from "@/config/interfaces";

interface LineProps {
    title: string;
    seriesName: string;
    height: number;
    yAxis: number[];
    xAxis: string[];
    showLabel: boolean;
    isBtnActive: boolean;
    onClick: () => void;
    extraYAxis?: extraYAxis[];
    btnText?: string;
}

export default function MultiLine( { title, seriesName, height, yAxis, xAxis, showLabel, isBtnActive, onClick, extraYAxis, btnText }: LineProps) {

const options = getChartOptions({ seriesName, xAxis, yAxis, showLabel, extraYAxis });

return (
    <>
        <div className="flex justify-between">
            <h3 className="text-xl font-semibold text-gray-900 dark:text-gray-100">
                {title}
            </h3>
        </div>
        <div>
            <Chart options={options} series={options.series} type="area" height={height} />
        </div>
        {isBtnActive && (
            <div className="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
                <div className="flex justify-between items-center pt-4">
                    <CustomButton onClick={onClick} className="uppercase text-sm font-semibold">
                        {btnText || "Ver detalhes"}
                        <svg className="w-2.5 h-2.5 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 9 4-4-4-4" />
                        </svg>
                    </CustomButton>
                </div>
            </div>
        )}
    </>
);
};