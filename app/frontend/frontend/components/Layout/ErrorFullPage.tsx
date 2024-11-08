"use client";

interface ErrorFullPageProps {
    error: string;
}

export default function ErrorFullPage({ error }: ErrorFullPageProps) {

    return (
        <div className="flex justify-center items-center h-full">
            <h1 className="text-red-500">{error}</h1>
        </div>
    );
}