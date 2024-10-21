"use client"

import Card from "@/components/Layout/Card";

interface ErrorCardProps {
    error: string | null;
    colspan: number;
    rowspan: number;
}

export default function ErrorCard({ error, colspan, rowspan }: ErrorCardProps ) {
    if (!error) return null;
    return (
        <Card colspan={colspan} rowspan={rowspan}>
            <div className={"text-red-500 text-center"}>
                {error}
            </div>
        </Card>
    )
}