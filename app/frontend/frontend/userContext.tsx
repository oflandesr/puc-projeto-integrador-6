"use client";
import React, { createContext, useState, useContext, ReactNode, useEffect } from 'react';
import {usePathname, useRouter} from 'next/navigation';
import {Bars} from "react-loading-icons";

interface UserData {
    user?: {
        id: number;
        firstName: string;
        lastName: string;
        access: {
            login: string;
            enabled: boolean;
            roles: {
                role: string;
                description: string;
            }[];
        };
    };
    return?: {
        id: string;
        message: string;
    };
}

interface UserContextType {
    getUserData: () => UserData;
    updateUserData: (data: UserData) => void;
    userId: string | null;
    updateUserId: (id: string | null) => void;
    resetLocalStorage: () => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const router = useRouter();
    const pathname = usePathname();

    const [userData, setUserData] = useState<UserData>({});
    const [userId, setUserId] = useState<string | null>(null);
    const [loading, setLoading] = useState(true); // New loading state

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const storedData = localStorage.getItem('userData');
            const storedId = localStorage.getItem('userId');

            if (storedData) {
                setUserData(JSON.parse(storedData));
            }
            setUserId(storedId);
        }
        setLoading(false); // Data loading completed
    }, []);

    useEffect(() => {
        if (!loading) {
            if (typeof window !== 'undefined') {
                localStorage.setItem('userData', JSON.stringify(userData));
            }
        }
    }, [userData, loading]);

    useEffect(() => {
        if (!loading) {
            if (typeof window !== 'undefined') {
                if (userId !== null) {
                    localStorage.setItem('userId', userId);
                } else {
                    localStorage.removeItem('userId');
                }
            }
        }
    }, [userId, loading])

    useEffect(() => {
        if (!loading && (!userId || Object.keys(userData).length === 0)) {
            // Only redirect if the user is NOT on the /register page
            if (pathname !== '/register') {
                router.push('/');
            }
        }
    }, [loading, userId, userData, pathname, router]);

    const getUserData = () => userData;
    const updateUserData = (data: UserData) => setUserData(data);
    const updateUserId = (id: string | null) => setUserId(id);
    const resetLocalStorage = () => {
        if (typeof window !== 'undefined') {
            localStorage.removeItem('userData');
            localStorage.removeItem('userId');
        }
        setUserData({});
        setUserId(null);
        router.push('/');
    };

    if (loading) {
        return (
            <section className="bg-gray-50 dark:bg-gray-900">
                <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                    <Bars className="w-10 h-10 text-red-600"  fill={'blue'}/>
                </div>
            </section>
        );
    }

    return (
        <UserContext.Provider value={{getUserData, updateUserData, userId, updateUserId, resetLocalStorage}}>
            {children}
        </UserContext.Provider>
    );
};

export const useUser = (): UserContextType => {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error('useUser must be used within a UserProvider');
    }
    return context;
};
