"use client";

import React, {useEffect} from "react";
import {useUser} from "@/userContext";
import Line from "@/components/Charts/Line";
import Card from "@/components/Layout/Card";
import Table from "@/components/Table";

interface UserTableData {
    id: number;
    name: string;
    age: number;
    email: string;
}

const userData: UserTableData[] = [
    { id: 1, name: 'John Doe', age: 28, email: 'john@example.com' },
    { id: 2, name: 'Jane Smith', age: 34, email: 'jane@example.com' },
    { id: 3, name: 'Alice Johnson', age: 25, email: 'alice@example.com' },
];

const userColumns = [
    {
        name: 'Name',
        selector: (row: UserTableData) => row.name,
        sortable: true,
    },
    {
        name: 'Age',
        selector: (row: UserTableData) => row.age,
        sortable: true,
    },
    {
        name: 'Email',
        selector: (row: UserTableData) => row.email,
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
                <Table columns={userColumns} data={userData} />;
            </Card>
        </>
    );
}
