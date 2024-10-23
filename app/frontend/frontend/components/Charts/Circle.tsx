"use client";

import React, {useEffect, useState} from "react";
import { ApexOptions } from "apexcharts";
import Chart from "react-apexcharts";
import CustomButton from "@/components/CustomButton";
// TODO https://flowbite.com/docs/plugins/charts/
interface CircleProps {
    title: string;
    height: number;
    onclick?: () => void;
    data: number[];
    labels: string[];
    btnText?: string;
}

export default function Circle({ title, height, onclick, data, labels, btnText }: CircleProps) {

    const [options, setOptions] = useState<ApexOptions>({
        chart: {
            height: "100%",
            width: "100%",
            type: "donut",
            fontFamily: "Inter, sans-serif",
            dropShadow: {
                enabled: false,
                blur: 5,
                opacity: 0.15,
            },
            toolbar: {
                show: false,
            },
        },
        tooltip: {
            enabled: true,
        },
        dataLabels: {
            enabled: true,
            style: {
                fontSize: "14px",
                fontFamily: "Inter, sans-serif",
                fontWeight: "bold",
                colors: ["#000000"],
            },
            dropShadow: {
                enabled: false,
            },
        },
        labels: labels,
        stroke: {
            width: 2,
            colors: ['#fff'],
        },
        series: data,
        colors: ["#1A56DB", "#009688", "#FF6F61", "#FFEB3B", "#9C27B0", "#FF9800", "#4CAF50"],
        legend: {
            show: true,
            position: 'bottom',
        },
    });

    useEffect(() => {
        // If data is not available, return just 0
        if (!data) {
            setOptions({
                ...options,
                series: [0],
            });
        } else {
            setOptions({
                ...options,
                series: data,
            });
        }
    }, [data]);

    return (
        <>
            <div className="flex justify-between items-center w-full text-center">
                <p className="text-1xl font-semibold">{title}</p>
            </div>
            <div>
                <Chart options={options} series={options.series} type="donut" height={height} />
            </div>
            <div className="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
                <div className="flex justify-between items-center pt-4">
                    <CustomButton onClick={onclick} className="uppercase text-sm font-semibold">
                        {btnText || "Ver detalhes"}
                        <svg className="w-2.5 h-2.5 ms-1.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 9 4-4-4-4"/>
                        </svg>
                    </CustomButton>
                </div>
            </div>
        </>
    );
}
