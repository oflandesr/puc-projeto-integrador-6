"use client";

import { useState, useEffect, useMemo } from 'react';
import axiosInstance from "@/config/axiosInstance";
import { createBasicAuthHeader } from "@/config/helpers";

// TODO: This may be causing an infinite loop, check if it's necessary to use JSON.stringify on the params object to avoid it

const useGet = <T>(
    url: string,
    params: Record<string, never> = {},
    auth?: { username: string | null | undefined; password: string | null | undefined }
) => {
    const [data, setData] = useState<T | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    const memoizedParams = useMemo(() => JSON.stringify(params), [params]);
    const memoizedAuth = useMemo(
        () => (auth && auth.username && auth.password ? auth : null),
        [auth?.username, auth?.password]
    );

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true); // Reinicia o estado de carregamento para chamadas subsequentes
            try {
                const headers: Record<string, string> = {};

                // Configura o cabeçalho de autenticação se as credenciais forem fornecidas
                if (memoizedAuth) {
                    headers['Authorization'] = createBasicAuthHeader(memoizedAuth.username!, memoizedAuth.password!);
                } else {
                    console.error("No auth credentials provided");
                    throw new Error("No auth credentials provided");
                }

                const response = await axiosInstance.get<T>(url, { params: JSON.parse(memoizedParams), headers });
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
    }, [url, memoizedParams, memoizedAuth]);

    return { data, error, loading };
};

export default useGet;
