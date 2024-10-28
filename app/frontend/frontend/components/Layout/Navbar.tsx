"use client";
import Link from 'next/link';
import { FiHome, FiSettings, FiUser , FiBriefcase} from 'react-icons/fi';
import { AiOutlineDashboard } from "react-icons/ai";
import { BsGraphUp } from "react-icons/bs";
import { GrTransaction } from "react-icons/gr";
import { usePathname } from 'next/navigation'; // For active link styling
import {useParams, useRouter} from "next/navigation";
import { IoPinOutline } from "react-icons/io5";
import { HiOutlineVariable } from "react-icons/hi2";

interface MenuItem {
    name: string;
    icon: JSX.Element;
    href: string;
    opposite?: boolean;
}

export default function Navbar() {
    const pathname = usePathname(); // Get current path
    const { walletId } = useParams();
    

    let menuItems: MenuItem[] = [
        { name: "Dashboard", icon: <AiOutlineDashboard />, href: "/dashboard" },
        { name: "Add Wallet", icon: <FiBriefcase />, href: "/dashboard/wallet" },
        { name: "Settings", icon: <FiSettings />, href: "/dashboard/settings" , opposite: true },
    ];

    // If we are inside a wallet, we need to get the wallet id and chnges the menu items
    if(walletId) {
        menuItems = [
            { name: "Dashboard", icon: <AiOutlineDashboard />, href: "/dashboard" },
            { name: "Home", icon: <FiHome />, href: `/dashboard/wallet/${walletId}` },
            { name: "Tickers", icon: <BsGraphUp />, href: `/dashboard/wallet/${walletId}/tickers` },
            { name: "Manage Fixed", icon: <IoPinOutline />, href: `/dashboard/wallet/${walletId}/fixed` },
            { name: "Add Variable", icon: <GrTransaction />, href: `/dashboard/wallet/${walletId}/add-variable` },
            { name: "Variable", icon: <HiOutlineVariable />, href: `/dashboard/wallet/${walletId}/variable-transactions` },
            { name: "Settings", icon: <FiSettings />, href: "/dashboard/settings" , opposite: true },
        ];
    }

    return (
        <div className="h-auto w-64 shadow-lg bg-white text-black p-6 flex flex-col justify-between">
            <nav className="flex flex-col gap-4">
                {menuItems.filter(item => !item.opposite).map((item) => (
                    <Link key={item.name} href={item.href} className={`p-3 flex items-center gap-3 transition-colors duration-200 rounded-lg 
                            ${pathname === item.href ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}>
                        <span className="text-xl">{item.icon}</span>
                        <span>{item.name}</span>
                    </Link>
                ))}
            </nav>
            <nav className="flex flex-col gap-4 mt-auto">
                {menuItems.filter(item => item.opposite).map((item) => (
                    <Link key={item.name} href={item.href} className={`p-3 flex items-center gap-3 transition-colors duration-200 rounded-lg 
                            ${pathname === item.href ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}>
                        <span className="text-xl">{item.icon}</span>
                        <span>{item.name}</span>
                    </Link>
                ))}
            </nav>
        </div>
    );
}