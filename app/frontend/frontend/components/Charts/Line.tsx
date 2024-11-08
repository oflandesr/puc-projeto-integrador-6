"use client";

import React  from "react";
import {ApexOptions} from "apexcharts";
import Chart from "react-apexcharts";
import CustomButton from "@/components/Button/CustomButton";

interface LineProps {
    title: string;
    height: number;
    onclick?: () => void;
    btnText?: string;
}

export default function Line({title, height, onclick, btnText}: LineProps) {

    const random7values = () => {
        return Array.from({length: 7}, () => Math.floor(Math.random() * 100));
    }

    const random7days = () => {
        return Array.from({length: 7}, () => Math.floor(Math.random() * 100));
    }

    const options: ApexOptions = {
        chart: {
            height: "100%",
            width: "100%",
            type: "area",
            fontFamily: "Inter, sans-serif",
            dropShadow: {
                enabled: false,
            },
            toolbar: {
                show: false,
            },
        },
        tooltip: {
            enabled: true,
            x: {
                show: false,
            },
        },
        fill: {
            type: "gradient",
            gradient: {
                opacityFrom: 0.55,
                opacityTo: 0,
                shade: "#1C64F2",
                gradientToColors: ["#1C64F2"],
            },
        },
        dataLabels: {
            enabled: false,
        },
        stroke: {
            width: 6,
        },
        grid: {
            show: false,
            strokeDashArray: 4,
            padding: {
                left: 2,
                right: 2,
                top: 0,
            },
        },
        series: [
            {
                name: "Account Balance",
                data: random7values(),
                color: "#1A56DB",
            },
        ],
        xaxis: {
            categories: random7days(),
            labels: {
                show: false,
            },
            axisBorder: {
                show: false,
            },
            axisTicks: {
                show: false,
            },
        },
        yaxis: {
            show: false,
        },
    };

    return (
        <>
            <div className="flex justify-between">
                <div>
                    <h5 className="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2">32.4k</h5>
                    <p className="text-base font-normal text-gray-500 dark:text-gray-400">{title}</p>
                </div>
                <div
                    className="flex items-center px-2.5 py-0.5 text-base font-semibold text-green-500 dark:text-green-500 text-center">
                    12%
                    <svg className="w-3 h-3 ms-1" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                         viewBox="0 0 10 14">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                              d="M5 13V1m0 0L1 5m4-4 4 4"/>
                    </svg>
                </div>
            </div>
            <div>
                <Chart options={options} series={options.series} type="area" height={height} />
            </div>
            <div className="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
                <div className="flex justify-between items-center pt-4">
                    <CustomButton
                        onClick={onclick}
                        className="uppercase text-sm font-semibold">
                        {btnText || "Ver detalhes"}
                        <svg className="w-2.5 h-2.5 ms-1.5 rtl:rotate-180" aria-hidden="true"
                             xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                                  d="m1 9 4-4-4-4"/>
                        </svg>
                    </CustomButton>
                </div>
            </div>
        </>
    );
}
