"use client";

import { useState, useEffect } from 'react';
import axiosInstance from "@/config/axiosInstance";
import {createBasicAuthHeader} from "@/config/helpers";

const useGet = <T>(url: string, params?: Record<string, never>, auth?: { username: string; password: string }) => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const headers: Record<string, string> = {};

                // If auth credentials are provided, add Basic Auth header
                if (auth) {
                    headers['Authorization'] = createBasicAuthHeader(auth.username, auth.password);
                } else {
                    console.error("No auth credentials provided");
                    new Error("No auth credentials provided");
                }

                const response = await axiosInstance.get<T>(url, { params, headers });
                if (response.status === 200 || response.status === 201) {
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
