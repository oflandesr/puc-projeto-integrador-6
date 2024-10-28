// useFetchTicker.js
import { useEffect, useState } from "react";
import { useUser } from "@/userContext";
import useGet from "./API/useGet";
import { Ticker } from "@/config/interfaces";

export default function useFetchTicker(tickerId : string) {
    const { getUserData, getUserPassword } = useUser();
    const [ticker, setTicker] = useState<Ticker | null>(null);
    const { data: tickers, error, loading } = useGet<Ticker[]>(
        `/ticker`,
        {},
        { username: getUserData().login, password: getUserPassword() }
    );

    useEffect(() => {
        if (tickers) {
            const foundTicker = tickers.find(t => t.ticker === tickerId);
            setTicker(foundTicker || null);
        }
    }, [tickers, tickerId]);

    return { ticker, loading, error };
}
