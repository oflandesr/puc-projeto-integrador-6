"use client";

import React, {useEffect, useState} from "react";
import { ApexOptions } from "apexcharts";
import Chart from "react-apexcharts";
import CustomButton from "@/components/Button/CustomButton";
// TODO https://flowbite.com/docs/plugins/charts/

interface CircleProps {
    title: string;
    height: number;
    onclick?: () => void;
    data: number[];
    labels: string[];
    btnText?: string;
}

export default function Circle({ title, height, onclick , data, labels, btnText }: CircleProps) {

    const [options, setOptions] = useState<ApexOptions>({
        series: data,
        labels: labels,
        chart: {
            height: 320,
            width: "100%",
            type: "donut",
        },
        tooltip: {
            enabled: true,
        },
        dataLabels: {
            enabled: false,
        },
        grid: {
            padding: {
                top: -2,
            },
        },
        stroke: {
            colors: ["transparent"],
            lineCap: undefined,
        },
        colors: ["#1A56DB", "#009688", "#FF6F61", "#FFEB3B", "#9C27B0", "#FF9800", "#4CAF50"],
        legend: {
            position: "bottom",
            fontFamily: "Inter, sans-serif",
        },
        plotOptions: {
            pie: {
                donut: {
                    labels: {
                        show: true,
                        name: {
                            show: true,
                            fontFamily: "Inter, sans-serif",
                            offsetY: 20,
                        },
                        total: {
                            showAlways: true,
                            show: true,
                            label: "Distribuição",
                            fontFamily: "Inter, sans-serif",
                            formatter: function (w) {
                                const sum = w.globals.seriesTotals.reduce((a: never, b: never) => {
                                    return a + b
                                }, 0)
                                return sum + '%'
                            },
                        },
                        value: {
                            show: true,
                            fontFamily: "Inter, sans-serif",
                            offsetY: -20,
                            formatter: function (value) {
                                return value + "%";
                            },
                        },
                    },
                    size: "80%",
                },
            },
        },
    });

    useEffect(() => {
        if (typeof window !== "undefined") {
            // Verifica se os dados estão disponíveis, se não, define o valor padrão
            if (!data) {
                setOptions((prevOptions) => ({
                    ...prevOptions,
                    series: [0],
                }));
            } else {
                setOptions((prevOptions) => ({
                    ...prevOptions,
                    series: data,
                }));
            }
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
