"use client";
import { useState } from 'react';
import axiosInstance from "@/config/axiosInstance";

const usePost = <T, U>() => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const postData = async (url: string, payload: U) => {
        setLoading(true);
        try {
            const response = await axiosInstance.post<T>(url, payload);
            if (response.status === 201) {
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
