"use client";

import { Bars } from 'react-loading-icons'

export default function LoadingFullPage() {

    return (
        <div className="flex justify-center items-center h-full">
            <Bars className="w-10 h-10" fill={'blue'} />
        </div>
    );
}