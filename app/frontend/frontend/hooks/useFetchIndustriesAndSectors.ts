import { useEffect, useState } from "react";
import { Ticker } from "@/config/interfaces";

const useFetchIndustriesAndSectors = (tickers: Ticker[]) => {
    const [availableIndustries, setAvailableIndustries] = useState<string[]>([]);
    const [availableSectors, setAvailableSectors] = useState<string[]>([]);

    useEffect(() => {
        const getIndustries = () => {
            const industries = tickers?.map(ticker => ticker.industry) || [];
            // Create a unique array of industries
            const uniqueIndustries = Array.from(new Set(industries));
            // Add 'All' to the beginning of the array
            uniqueIndustries.unshift("All");
            // Set the state with the modified array
            setAvailableIndustries(uniqueIndustries);
        };

        const getSectors = () => {
            const sectors = tickers?.map(ticker => ticker.sector) || [];
            // Create a unique array of sectors
            const uniqueSectors = Array.from(new Set(sectors));
            // Add 'All' to the beginning of the array
            uniqueSectors.unshift("All");
            // Set the state with the modified array
            setAvailableSectors(uniqueSectors);
        };

        getIndustries();
        getSectors();
    }, [tickers]);

    return { availableIndustries, availableSectors };
};


export default useFetchIndustriesAndSectors;
