// /app/dashboard/layout.tsx
import { ReactNode } from 'react';
import Navbar from "@/components/Layout/Navbar";

interface LayoutProps {
    children: ReactNode;
}

export default function DashboardLayout({ children }: LayoutProps) {
    return (
        <div className="flex min-h-screen">
            {/* Left Sidebar Navbar */}
            <Navbar />

            {/* Main Content with Grid Layout */}
            <div className="flex-1 p-4 min-h-screen">
            <div className="grid grid-cols-12 gap-4">
                    {children}
                </div>
            </div>
        </div>
    );
}
