"use client";

import React, {useEffect} from "react";
import {useUser} from "@/userContext";
import Line from "@/components/Charts/Line";
import Card from "@/components/Layout/Card";
import Table from "@/components/Table";
import Link from "next/link";

interface InterfaceUserTableData {
    id: number;
    name: string;
    objective: string;
    intenFixIncPercent: string;
    intenStockPercent: string;
    intenFilPercent: string;
}

const tableData: InterfaceUserTableData[] = [
    { id: 1, name: "John Doe", objective: "Objective 1", intenFixIncPercent: "10", intenStockPercent: "20", intenFilPercent: "70" },
    { id: 2, name: "Jane Johnson", objective: "Objective 2", intenFixIncPercent: "20", intenStockPercent: "30", intenFilPercent: "50" },
    { id: 3, name: "John Smith", objective: "Objective 3", intenFixIncPercent: "30", intenStockPercent: "40", intenFilPercent: "30" },
    { id: 4, name: "Jane Smith", objective: "Objective 4", intenFixIncPercent: "40", intenStockPercent: "50", intenFilPercent: "10" },
    { id: 5, name: "John Johnson", objective: "Objective 5", intenFixIncPercent: "50", intenStockPercent: "30", intenFilPercent: "20" },
];

const tableCols = [
    {
        name: 'Wallet Name',
        selector: (row: InterfaceUserTableData) => row.name,
        sortable: true,
    },
    {
        name: 'Objective',
        selector: (row: InterfaceUserTableData) => row.objective,
        sortable: true,
    },
    {
        name: 'Inten Fix Inc %',
        selector: (row: InterfaceUserTableData) => row.intenFixIncPercent,
        sortable: true,
    },
    {
        name: 'Inten Stock %',
        selector: (row: InterfaceUserTableData) => row.intenStockPercent,
        sortable: true,
    },
    {
        name: 'Inten Fil %',
        selector: (row: InterfaceUserTableData) => row.intenFilPercent,
        sortable: true,
    },
    // Add a button to access the wallet
    {
        name: 'Access',
        selector: (row: InterfaceUserTableData) => <Link href={`/dashboard/wallet/${row.id}`} className={"text-blue-500"}><span>Access Wallet</span></Link>,
        sortable: false,
    }
];

export default function Home() {
    const {getUserData, userId} = useUser();

    useEffect(() => {
        console.log(getUserData());
    }, [getUserData, userId]);

    return (
        <>
            <Card colspan={6} rowspan={1}>
                <Line
                    title="Revenue"
                    height={250}
                />
            </Card>
            <Card colspan={6} rowspan={1}>
                <Line
                    title="Stock"
                    height={250}
                />
            </Card>
            <Card colspan={12} rowspan={1}>
                {/*Expect error below if using a button on one of the rows*/}
                <Table columns={tableCols} data={tableData} />
            </Card>
        </>
    );
}
