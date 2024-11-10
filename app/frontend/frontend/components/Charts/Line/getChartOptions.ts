import { extraYAxis } from "@/config/interfaces";
import { ApexOptions } from "apexcharts";

interface ChartConfigProps {
    seriesName: string;
    xAxis: string[];
    yAxis: number[];
    showLabel: boolean;
    extraYAxis?: extraYAxis[];
}

const getMultiLineChartOptions = ({ seriesName, xAxis, yAxis, showLabel, extraYAxis }: ChartConfigProps): ApexOptions => {
    const series = [
        {
            name: seriesName,
            data: yAxis,
            color: "#1A56DB",
        },
    ];

    // Add extra Y-axis data series dynamically
    if (extraYAxis && extraYAxis.length > 0) {
        extraYAxis.forEach((data, index) => {
            series.push({
                name: data.name,
                data: data.extraYAxis,
                color: `#${Math.floor(Math.random()*16777215).toString(16)}`, // Generate random color or set predefined colors
            });
        });
    }

    return {
        chart: {
            height: "100%",
            width: "100%",
            type: "area",
            fontFamily: "Inter, sans-serif",
            dropShadow: { enabled: false },
            toolbar: { show: false },
        },
        tooltip: {
            enabled: true,
            x: {
                show: false, // Hide the x-axis value in the tooltip
            },
            shared: true, // Display values from all series at the same time
            intersect: false, // Allow tooltips to show when hovering between series
            y: {
                formatter: (val) => `${val}`, // Customize how the Y values are shown
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
        dataLabels: { enabled: false },
        stroke: { width: 6 },
        grid: {
            show: false,
            strokeDashArray: 2,
            padding: { left: 2, right: 2, top: 0 },
        },
        series, // Use the dynamically generated series array
        xaxis: {
            categories: xAxis,
            labels: { show: showLabel },
            axisBorder: { show: false },
            axisTicks: { show: false },
        },
        yaxis: { show: false },
    };
};

export default getMultiLineChartOptions;
