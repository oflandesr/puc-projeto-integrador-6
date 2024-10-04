"use client";

import React, {useEffect} from "react";
import {ApexOptions} from "apexcharts";
import Chart from "react-apexcharts";
import {useUser} from "@/userContext";


export default function Home() {
    const {getUserData, userId} = useUser();

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
                data: [6500, 6418, 6456, 6526, 6356, 6456],
                color: "#1A56DB",
            },
        ],
        xaxis: {
            categories: ['01 February', '02 February', '03 February', '04 February', '05 February', '06 February', '07 February'],
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

    useEffect(() => {
        console.log(getUserData());
    }, [getUserData, userId]);

    return (
        <div className={`col-span-4 row-span-12 bg-white rounded-lg shadow dark:bg-gray-800 p-4 md:p-6`}>
            <div className="flex justify-between">
                <div>
                    <h5 className="leading-none text-3xl font-bold text-gray-900 dark:text-white pb-2">32.4k</h5>
                    <p className="text-base font-normal text-gray-500 dark:text-gray-400">Account Balance</p>
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
                <Chart options={options} series={options.series} type="area" height="250" />
            </div>
            <div className="grid grid-cols-1 items-center border-gray-200 border-t dark:border-gray-700 justify-between">
                <div className="flex justify-between items-center pt-4">
                    <a
                        href="#"
                        className="uppercase w-full text-center text-sm font-semibold inline-flex justify-center items-center rounded-lg text-blue-600 hover:text-blue-700 dark:hover:text-blue-500 hover:bg-gray-100 dark:hover:bg-gray-700 dark:focus:ring-gray-700 dark:border-gray-700 px-3 py-2">
                        See Details
                        <svg className="w-2.5 h-2.5 ms-1.5 rtl:rotate-180" aria-hidden="true"
                             xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                                  d="m1 9 4-4-4-4"/>
                        </svg>
                    </a>
                </div>
            </div>
        </div>
    );
}
