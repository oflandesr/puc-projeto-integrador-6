"use client";
import { useState } from 'react';
import axiosInstance from "@/config/axiosInstance";
import {createBasicAuthHeader} from "@/config/helpers";

const usePost = <T, U>() => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const postData = async (
        url: string,
        payload: U,
        auth?: { username: string | null | undefined; password: string | null | undefined }
    ) => {
        setLoading(true);
        try {
            const headers: Record<string, string> = {};

            // If auth credentials are provided, add Basic Auth header
            if (auth && auth.username && auth.password) {
                headers['Authorization'] = createBasicAuthHeader(auth.username, auth.password);
            } else {
                console.error("No auth credentials provided");
                new Error("No auth credentials provided");
            }

            const response = await axiosInstance.post<T>(url, payload, { headers });

            if (response.status === 201 || response.status === 200) {
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

    return { data, error, loading, postData };
};

export default usePost;
