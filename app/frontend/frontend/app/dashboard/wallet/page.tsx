"use client";

import React, {useEffect} from "react";
import {useUser} from "@/userContext";
import Line from "@/components/Charts/Line";
import Card from "@/components/Layout/Card";
import Table from "@/components/Table";

interface InterfaceUserTableData {
    id: number;
    name: string;
    age: number;
    email: string;
}

const tableData: InterfaceUserTableData[] = [
    { id: 1, name: 'John Doe', age: 28, email: 'john@example.com' },
    { id: 2, name: 'Jane Smith', age: 34, email: 'jane@example.com' },
    { id: 3, name: 'Alice Johnson', age: 25, email: 'alice@example.com' },
    { id: 4, name: 'Gabriel SIlva', age: 29, email: 'gabriel@example.com' },
    { id: 5, name: 'Filipe', age: 29, email: 'filipe@example.com' },
];

const tableCols = [
    {
        name: 'Name',
        selector: (row: InterfaceUserTableData) => row.name,
        sortable: true,
    },
    {
        name: 'Age',
        selector: (row: InterfaceUserTableData) => row.age,
        sortable: true,
    },
    {
        name: 'Email',
        selector: (row: InterfaceUserTableData) => row.email,
        sortable: true,
    },
];

export default function Home() {
    const {getUserData, userId} = useUser();

    useEffect(() => {
        console.log(getUserData());
    }, [getUserData, userId]);

    return (
        <>
            <Card colspan={12} rowspan={1}>
                Test
            </Card>
        </>
    );
}
