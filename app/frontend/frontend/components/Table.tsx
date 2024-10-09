"use client";
import React from 'react';
import DataTable from 'react-data-table-component';
import CustomInput from '@/components/CustomInput';
import useTable from "@/hooks/useTable";

interface TableProps<T> {
    columns: Array<{ name: string; selector: (row: T) => string | number; sortable: boolean }>;
    data: T[];
}

export default function Table<T>({ columns, data }: TableProps<T>) {
    const { search, setSearch, filteredData } = useTable({ data, columns });

    return (
        <div>
            {/* Search Bar */}
            <CustomInput
                id="searchBarTable"
                name="searchBar"
                type="text"
                placeholder={`Search by ` + columns.map((col) => col.name).join(', ')}
                value={search}
                onChange={(e) => setSearch(e.target.value)}
            />

            <DataTable
                columns={columns}
                data={filteredData} // Use filtered data
                pagination // Enable pagination
                paginationPerPage={4} // Limit to 5 rows per page
                paginationRowsPerPageOptions={[4, 8, 12]} // Rows per page options
                defaultSortFieldId={1} // Optional: default sorting by Name
            />
        </div>
    );
}
