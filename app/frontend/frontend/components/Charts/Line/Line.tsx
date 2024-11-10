"use client";

import ChartHeader from "./ChartHeader";
import getChartOptions from "./getChartOptions";
import PerformanceLabel from "./PerformanceLabel";
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

export default function Line( { title, seriesName, height, yAxis, xAxis, showLabel, isBtnActive, onClick, extraYAxis, btnText }: LineProps) {

const latestValue = yAxis[yAxis.length - 1];
const startDate = String(xAxis[0]);
const endDate = String(xAxis[xAxis.length - 1]);
const options = getChartOptions({ seriesName, xAxis, yAxis, showLabel, extraYAxis });

return (
    <>
        <div className="flex justify-between">
            <ChartHeader title={title} latestValue={latestValue} startDate={startDate} endDate={endDate} />
            {/* Show only if extraYAxis is NOT present */} 
            {!extraYAxis && 
                <PerformanceLabel start={yAxis[0]} end={latestValue} />
            }
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