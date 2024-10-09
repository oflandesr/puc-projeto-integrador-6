import { useState, useMemo } from "react";

interface UseTableProps<T> {
    data: T[];
    columns: Array<{ name: string; selector: (row: T) => string | number; sortable: boolean }>;
}

export default function useTable<T>({ data, columns }: UseTableProps<T>) {
    const [search, setSearch] = useState<string>("");

    const filteredData = useMemo(() => {
        if (!search) return data;
        return data.filter((item) =>
            columns.some((col) =>
                String(col.selector(item)).toLowerCase().includes(search.toLowerCase())
            )
        );
    }, [data, search, columns]);

    return {
        search,
        setSearch,
        filteredData,
    };
}
