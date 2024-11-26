"use client";
import Link from 'next/link';
import { FiHome, FiUser, FiPieChart } from 'react-icons/fi';
import { AiOutlineDashboard, AiOutlineWallet } from "react-icons/ai";
import { BsBarChart } from "react-icons/bs";
import { GrTransaction } from "react-icons/gr";
import { usePathname } from 'next/navigation';
import { useParams } from "next/navigation";
import { IoStatsChartOutline } from "react-icons/io5";
import { HiBookOpen, HiChartBar, HiDocumentMagnifyingGlass, HiOutlineBookOpen, HiOutlineCurrencyDollar, HiOutlineDocumentMagnifyingGlass, HiPlus } from "react-icons/hi2";
import { useState } from "react";
import { PiFireSimpleDuotone } from 'react-icons/pi';
import { IoIosArrowDown, IoIosArrowUp } from 'react-icons/io'; // Import icons for dropdown toggle

interface MenuItem {
    name: string;
    icon: JSX.Element;
    href: string;
    opposite?: boolean;
    subMenu?: MenuItem[]; // Added subMenu to hold dropdown items
}

export default function Navbar() {
    const pathname = usePathname();
    const { walletId } = useParams();

    const [isFixedOpen, setIsFixedOpen] = useState(false);
    const [isVariableOpen, setIsVariableOpen] = useState(false);

    let menuItems: MenuItem[] = [
        { name: "Visão Geral", icon: <AiOutlineDashboard />, href: "/dashboard" },
        { name: "Adicionar Carteira", icon: <AiOutlineWallet />, href: "/dashboard/wallet" },
        { name: "Sair", icon: <FiUser />, href: "/", opposite: true },
    ];

    if (walletId) {
        menuItems = [
            { name: "Carteira", icon: <FiHome />, href: `/dashboard/wallet/${walletId}` },
            { name: "Ações", icon: <BsBarChart />, href: `/dashboard/wallet/${walletId}/tickers` },
            {
                name: "Investimentos Fixos", icon: <HiOutlineCurrencyDollar />, href: "#", subMenu: [
                    { name: "Adicionar / Editar", icon: <HiPlus />, href: `/dashboard/wallet/${walletId}/fixed` },
                    { name: "Rendimento por Instituição", icon: <HiOutlineBookOpen />, href: `/dashboard/wallet/${walletId}/overview/fixed` },
                    { name: "Gráficos de Rendimento", icon: <FiPieChart />, href: `/dashboard/wallet/${walletId}/fixed/charts` }
                ]
            },
            {
                name: "Investimentos Variáveis", icon: <IoStatsChartOutline />, href: "#", subMenu: [
                    { name: "Detalhes / Editar", icon: <HiOutlineDocumentMagnifyingGlass />, href: `/dashboard/wallet/${walletId}/variable` },
                    { name: "Rendimento por Instituição", icon: <HiOutlineBookOpen />, href: `/dashboard/wallet/${walletId}/overview/variable` },
                ]
            },
            { name: "Voltar", icon: <GrTransaction />, href: "/dashboard", opposite: true },
        ];
    }

    // Toggle functions for dropdowns
    const handleFixedClick = () => setIsFixedOpen(!isFixedOpen);
    const handleVariableClick = () => setIsVariableOpen(!isVariableOpen);

    return (
        <div className="h-auto w-64 shadow-lg bg-white text-black p-6 flex flex-col justify-between">
            <nav className="flex flex-col gap-4">
                {menuItems.filter(item => !item.opposite).map((item) => (
                    <div key={item.name}>
                        {/* Main Link */}
                        <Link
                            href={item.href}
                            className={`p-3 flex items-center gap-3 transition-colors duration-200 rounded-lg 
                            ${pathname === item.href ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}
                            onClick={item.subMenu ? (item.name === "Investimentos Fixos" ? handleFixedClick : handleVariableClick) : undefined}
                        >
                            <span className="text-xl">{item.icon}</span>
                            <span>{item.name}</span>

                            {/* Dropdown Icon */}
                            {item.subMenu && (item.name === "Investimentos Fixos" || item.name === "Investimentos Variáveis") && (
                                <span className="ml-auto">
                                    {item.name === "Investimentos Fixos" && (isFixedOpen ? <IoIosArrowUp /> : <IoIosArrowDown />)}
                                    {item.name === "Investimentos Variáveis" && (isVariableOpen ? <IoIosArrowUp /> : <IoIosArrowDown />)}
                                </span>
                            )}
                        </Link>

                        {/* Submenu for Fixed Investments */}
                        {item.subMenu && item.name === "Investimentos Fixos" && (
                            <div
                                className={`transition-all duration-300 ease-in-out overflow-hidden ml-4 mt-2 
                                    ${isFixedOpen ? 'max-h-96' : 'max-h-0'}`} // Animate the height change
                            >
                                {item.subMenu.map((subItem) => (
                                    <Link
                                        key={subItem.name}
                                        href={subItem.href}
                                        className={`p-3 flex items-center gap-3 transition-colors duration-200 rounded-lg 
                                        ${pathname === subItem.href ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}
                                    >
                                        <span className="text-xl">{subItem.icon}</span>
                                        <span>{subItem.name}</span>
                                    </Link>
                                ))}
                            </div>
                        )}

                        {/* Submenu for Variable Investments */}
                        {item.subMenu && item.name === "Investimentos Variáveis" && (
                            <div
                                className={`transition-all duration-300 ease-in-out overflow-hidden ml-4 mt-2 
                                    ${isVariableOpen ? 'max-h-96' : 'max-h-0'}`} // Animate the height change
                            >
                                {item.subMenu.map((subItem) => (
                                    <Link
                                        key={subItem.name}
                                        href={subItem.href}
                                        className={`p-3 flex items-center gap-3 transition-colors duration-200 rounded-lg 
                                        ${pathname === subItem.href ? 'bg-blue-500 text-white' : 'hover:bg-gray-100'}`}
                                    >
                                        <span className="text-xl">{subItem.icon}</span>
                                        <span>{subItem.name}</span>
                                    </Link>
                                ))}
                            </div>
                        )}
                    </div>
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
