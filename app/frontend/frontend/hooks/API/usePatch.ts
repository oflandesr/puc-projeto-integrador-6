"use client";

import { useState } from 'react';
import axiosInstance from "@/config/axiosInstance";
import { createBasicAuthHeader } from "@/config/helpers";

const usePatch = <T>() => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const patchData = async (
        url: string,
        auth: { username: string | null | undefined; password: string | null | undefined },
        patchBody: any
    ) => {
        setLoading(true);
        setError(null); // Clear previous errors
        setData(null); // Clear previous data

        try {
            const headers: Record<string, string> = {};

            // If auth credentials are provided, add Basic Auth header
            if (auth && auth.username && auth.password) {
                headers['Authorization'] = createBasicAuthHeader(auth.username, auth.password);
            } else {
                setError("No auth credentials provided");
                setLoading(false);
                return;
            }

            // Faça o cast do patchBody para Record<string, any>
            const response = await axiosInstance.patch<T>(url, patchBody as Record<string, any>, {
                headers // Pass headers with the request
            });

            if (response.status === 200 || response.status === 201) {
                setData(response.data);
            } else {
                setError(`Unexpected status code: ${response.status}`);
            }
        } catch (err) {
            setError("Unknown error occurred: " + (err instanceof Error ? err.message : ""));
        } finally {
            setLoading(false);
        }
    };

    return { data, error, loading, patchData };
};

export default usePatch;
