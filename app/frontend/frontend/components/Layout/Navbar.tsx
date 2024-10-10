"use client";
import Link from 'next/link';
import { FiHome, FiSettings, FiUser , FiBriefcase} from 'react-icons/fi';
import { usePathname } from 'next/navigation'; // For active link styling

interface MenuItem {
    name: string;
    icon: JSX.Element;
    href: string;
    opposite?: boolean;
}

export default function Navbar() {
    const pathname = usePathname(); // Get current path

    const menuItems: MenuItem[] = [
        { name: "Home", icon: <FiHome />, href: "/dashboard" },
        { name: "Add Wallet", icon: <FiBriefcase />, href: "/dashboard/wallet" },
        { name: "Profile", icon: <FiUser />, href: "/dashboard/profile"},
        { name: "Settings", icon: <FiSettings />, href: "/dashboard/settings" , opposite: true },
    ];

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