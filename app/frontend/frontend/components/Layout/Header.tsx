"use client";

import React from "react";
import Card from "@/components/Layout/Card";

export default function Header({title} : {title: string}) {
    return (
        <Card colspan={12} rowspan={1}>
            <div className="flex justify-between items-center text-2xl font-semibold">
                {title}
            </div>
        </Card>        
    );
}