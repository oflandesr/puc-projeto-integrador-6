import React from "react";
import CustomDropdown from "@/components/Button/CustomDropdown";
import CustomInput from "@/components/Layout/CustomInput";

interface SearchFiltersProps {
    tickerSearch: string;
    setTickerSearch: React.Dispatch<React.SetStateAction<string>>;
    industrySearch: string;
    setIndustrySearch: React.Dispatch<React.SetStateAction<string>>;
    avaliableIndustries: string[];
    sectorSearch: string;
    setSectorSearch: React.Dispatch<React.SetStateAction<string>>;
    avaliableSectors: string[];
}

const SearchFilters: React.FC<SearchFiltersProps> = ({
    tickerSearch,
    setTickerSearch,
    industrySearch,
    setIndustrySearch,
    avaliableIndustries,
    sectorSearch,
    setSectorSearch,
    avaliableSectors,
}) => {
    return (
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
                    options={avaliableIndustries}
                    selected={industrySearch}
                    onChange={(e) => setIndustrySearch(e.target.value)}
                />
            </div>
            <div className="col-span-6">
                <CustomDropdown
                    label={null}
                    options={avaliableSectors}
                    selected={sectorSearch}
                    onChange={(e) => setSectorSearch(e.target.value)}
                />
            </div>
        </div>
    );
};

export default SearchFilters;
