"use client";
import Link from 'next/link';

export default function Navbar() {
    return (
        <div className="h-screen w-64 bg-gray-800 text-white p-4 flex flex-col">
            <div className="text-2xl font-bold mb-6">Dashboard</div>
            <nav className="flex flex-col gap-4">
                <Link href="/dashboard/profile" className="hover:bg-gray-700 p-2 rounded" >
                    Home
                </Link>
                <Link href="/dashboard/profile" className="hover:bg-gray-700 p-2 rounded" >
                    Settings
                </Link>
                <Link href="/dashboard/profile" className="hover:bg-gray-700 p-2 rounded" >
                    Profile
                </Link>
            </nav>
        </div>
    );
}
