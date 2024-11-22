"use client";
import Link from 'next/link';
import { FiHome, FiUser, FiPieChart } from 'react-icons/fi';
import { AiOutlineDashboard, AiOutlineWallet } from "react-icons/ai";
import { BsBarChart } from "react-icons/bs";
import { GrTransaction } from "react-icons/gr";
import { usePathname } from 'next/navigation';
import { useParams } from "next/navigation";
import { IoStatsChartOutline } from "react-icons/io5";
import { HiOutlineCurrencyDollar } from "react-icons/hi2";

interface MenuItem {
    name: string;
    icon: JSX.Element;
    href: string;
    opposite?: boolean;
}

export default function Navbar() {
    const pathname = usePathname();
    const { walletId } = useParams();

    let menuItems: MenuItem[] = [
        { name: "Visão Geral", icon: <AiOutlineDashboard />, href: "/dashboard" },
        { name: "Adicionar Carteira", icon: <AiOutlineWallet />, href: "/dashboard/wallet" },
        { name: "Sair", icon: <FiUser />, href: "/", opposite: true },
    ];

    if (walletId) {
        menuItems = [
            { name: "Carteira", icon: <FiHome />, href: `/dashboard/wallet/${walletId}` },
            { name: "Ações", icon: <BsBarChart />, href: `/dashboard/wallet/${walletId}/tickers` },
            { name: "Invest. Fixos", icon: <HiOutlineCurrencyDollar />, href: `/dashboard/wallet/${walletId}/fixed` },
            { name: "Invest. Variáveis", icon: <IoStatsChartOutline />, href: `/dashboard/wallet/${walletId}/variable` },
            { name: "Inv. Fixos - Gráficos", icon: <FiPieChart />, href: `/dashboard/wallet/${walletId}/fixed/charts` },
            { name: "Voltar", icon: <GrTransaction />, href: "/dashboard", opposite: true },
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
