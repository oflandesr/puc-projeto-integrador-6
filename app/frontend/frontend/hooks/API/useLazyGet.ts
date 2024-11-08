"use client";

import { useState } from 'react';
import axiosInstance from "@/config/axiosInstance";
import {createBasicAuthHeader} from "@/config/helpers";

const useLazyGet = <T>() => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const fetchData = async (
        url: string,
        auth: { username: string | null | undefined; password: string | null | undefined}, // auth can be an object or undefined
        params?: Record<string, never>, // params can be an object or undefined
    ) => {
        setLoading(true);
        setError(null); // Clear previous errors
        setData(null); // Clear previous data
        await new Promise(resolve => setTimeout(resolve, 2000));

        try {
            const headers: Record<string, string> = {};

            // If auth credentials are provided, add Basic Auth header
            if (auth && auth.username && auth.password) {
                headers['Authorization'] = createBasicAuthHeader(auth.username, auth.password);
            } else {
                console.error("No auth credentials provided");
                new Error("No auth credentials provided");
            }

            const response = await axiosInstance.get<T>(url, {
                params,
                headers // Pass headers with the request
            });

            if (response.status === 200 || response.status === 201) {
                setData(response.data);
            } else {
                setError(`Unexpected status code: ${response.status}`);
            }
        } catch (err) {
            setError("Unknown error occurred" + (err instanceof Error ? err.message : ""));
        } finally {
            setLoading(false);
        }
    };

    return { data, error, loading, fetchData };
};

export default useLazyGet;
