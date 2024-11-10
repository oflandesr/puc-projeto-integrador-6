"use client";

import React, { useEffect, useState } from "react";
import { useUser } from "@/userContext";
import { useParams, useRouter } from "next/navigation";
import Card from "@/components/Layout/Card";
import useGet from "@/hooks/Api/useGet";
import { Ticker } from "@/config/interfaces";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import CustomDropdown from "@/components/Button/CustomDropdown";
import CustomInput from "@/components/Layout/CustomInput";
import useFetchIndustriesAndSectors from "@/hooks/Filters/useFetchIndustriesAndSectors";
import useFilter from "@/hooks/Filters/useFilter";
import { tickerFilterFunction } from "@/config/helpers";
import TickerItem from "@/components/Ticker/Search/TickerItem";

export default function Home() {
    const router = useRouter();
    const { getUserData, userId, getUserPassword } = useUser();
    const { walletId } = useParams();

    const [tickerSearch, setTickerSearch] = useState<string>("");
    const [industrySearch, setIndustrySearch] = useState<string>("All");
    const [sectorSearch, setSectorSearch] = useState<string>("All");

    const { data: tickers, error: errorTickers, loading: loadingTickers } = useGet<Ticker[]>(`/ticker`, {}, { username: getUserData().login, password: getUserPassword() });

    const { availableIndustries, availableSectors } = useFetchIndustriesAndSectors(tickers || []);

    // console log our tickers
    useEffect(() => {
        console.log(tickers);
    }, [tickers]);

    // Define criteria object for filtering
    const filterCriteria = {
        tickerSearch,
        industrySearch,
        sectorSearch,
    };

    // Use the generic filter hook with the ticker filter function
    const filteredTickers = useFilter<Ticker, { tickerSearch: string; industrySearch: string; sectorSearch: string }>(
        tickers || [],
        filterCriteria,
        tickerFilterFunction
    );

    if (loadingTickers) {
        return <LoadingFullPage />;
    }

    if (errorTickers) {
        return (
            <Card colspan={12} rowspan={1}>
                <p className="text-red-500 font-semibold text-lg">
                    Erro ao carregar os tickers:
                </p>
                <p className="text-red-500">{errorTickers}</p>
            </Card>
        );
    }

    return (
        <>
            <Card colspan={12} rowspan={1}>
                <div className="grid grid-cols-12 gap-4">
                    <div className="col-span-12">
                        <CustomInput
                            value={tickerSearch}
                            onChange={(e) => setTickerSearch(e.target.value)}
                            placeholder="Search for a ticker"
                            type="text"
                            name="ticker"
                            id="ticker"
                        />
                    </div>
                    <div className="col-span-6">
                        <CustomDropdown
                            label={null}
                            options={availableIndustries}
                            selected={industrySearch}
                            onChange={(e) => setIndustrySearch(e.target.value)}
                        />
                    </div>
                    <div className="col-span-6">
                        <CustomDropdown
                            label={null}
                            options={availableSectors}
                            selected={sectorSearch}
                            onChange={(e) => setSectorSearch(e.target.value)}
                        />
                    </div>
                </div>
            </Card>
            {filteredTickers.map(ticker => (
                <TickerItem id={ticker.id} ticker={ticker} />
            ))}
        </>
    );
}
