"use client";

import { useState, useEffect } from 'react';
import axiosInstance from "@/config/axiosInstance";

const useGet = <T>(url: string, params?: Record<string, any>) => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosInstance.get<T>(url, { params });
                if (response.status === 200) {
                    setData(response.data);
                } else {
                    setError(`Unexpected status code: ${response.status}`);
                }
            } catch (err) {
                setError(err instanceof Error ? err.message : 'Unknown error occurred');
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [url, params]);

    return { data, error, loading };
};

export default useGet;
