"use client";

import { useState } from 'react';
import axiosInstance from "@/config/axiosInstance";
import Error from "next/error";

const useLazyGet = <T>(url: string, params?: Record<string, any>) => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const fetchData = async () => {
        setLoading(true);
        setError(null); // Clear previous errors
        setData(null); // Clear previous data
        await new Promise(resolve => setTimeout(resolve, 1000));
        try {
            const response = await axiosInstance.get<T>(url, { params });
            if (response.status === 200) {
                setData(response.data);
            } else {
                setError(`Unexpected status code: ${response.status}`);
            }
        } catch (err) {
            setError("Unknown error occurred");
        } finally {
            setLoading(false);
        }
    };

    return { data, error, loading, fetchData };
};

export default useLazyGet;
