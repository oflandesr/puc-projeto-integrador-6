import { useMemo } from "react";

function useFilter<T, C>(
    items: T[],
    criteria: C,
    filterFunction: (item: T, criteria: C) => boolean
) {
    return useMemo(() => {
        return items.filter(item => filterFunction(item, criteria));
    }, [items, criteria, filterFunction]);
}

export default useFilter;
