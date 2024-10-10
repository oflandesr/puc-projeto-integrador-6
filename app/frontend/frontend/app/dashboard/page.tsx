"use client";

import React, {useEffect} from "react";
import {useUser} from "@/userContext";
import Line from "@/components/Charts/Line";
import Card from "@/components/Layout/Card";
import Table from "@/components/Table";
import {tableCols, tableData} from "@/mock/mock";

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
