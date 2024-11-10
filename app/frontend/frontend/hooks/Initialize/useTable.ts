import { useState, useMemo } from "react";
import { TableColumn } from "react-data-table-component";

interface UseTableProps<T> {
    columns: TableColumn<T>[]; // Use TableColumn for the columns
    data: T[];
}

export default function useTable<T>({ data, columns }: UseTableProps<T>) {
    const [search, setSearch] = useState<string>("");

    const filteredData = useMemo(() => {
        if (!search) return data;

        return data.filter((item) =>
            columns.some((col) => {
                const value = col.selector ? col.selector(item) : "";

                // Only perform the search if the value is a string or number
                if (typeof value === "string" || typeof value === "number") {
                    return String(value).toLowerCase().includes(search.toLowerCase());
                }
                return false; // Skip JSX.Element in search
            })
        );
    }, [data, search, columns]);

    return {
        search,
        setSearch,
        filteredData,
    };
}
