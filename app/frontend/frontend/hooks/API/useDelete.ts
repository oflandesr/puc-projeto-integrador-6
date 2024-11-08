"use client";

import { useState } from 'react';
import axiosInstance from "@/config/axiosInstance";
import { createBasicAuthHeader } from "@/config/helpers";

const useDelete = <T>() => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const deleteData = async (
        url: string,
        auth: { username: string | null | undefined; password: string | null | undefined },
    ) => {
        setLoading(true);
        setError(null); // Clear previous errors
        setData(null); // Clear previous data
        await new Promise(resolve => setTimeout(resolve, 2000)); // Optional delay

        try {
            const headers: Record<string, string> = {};

            // Add Basic Auth header if credentials are provided
            if (auth && auth.username && auth.password) {
                headers['Authorization'] = createBasicAuthHeader(auth.username, auth.password);
            } else {
                console.error("No auth credentials provided");
                throw new Error("No auth credentials provided");
            }

            const response = await axiosInstance.delete<T>(url, {
                headers
            });

            if (response.status === 200 || response.status === 204 || response.status === 202) {
                setData(response.data || null);
            } else {
                setError(`Unexpected status code: ${response.status}`);
            }
        } catch (err) {
            setError("Unknown error occurred: " + (err instanceof Error ? err.message : ""));
        } finally {
            setLoading(false);
        }
    };

    return { data, error, loading, deleteData };
};

export default useDelete;
