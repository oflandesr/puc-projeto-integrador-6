"use client";
import React, { createContext, useState, useContext, ReactNode } from 'react';

interface UserData {
    [key: string]: never; // Adjust this as needed for specific user data properties
}

interface UserContextType {
    getUserData: () => UserData;
    updateUserData: (data: UserData) => void;
    userId: string | null;
    updateUserId: (id: string | null) => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [userData, setUserData] = useState<UserData>({});
    const [userId, setUserId] = useState<string | null>(null);

    const getUserData = () => userData;
    const updateUserData = (data: UserData) => setUserData(data);
    const updateUserId = (id: string | null) => setUserId(id);

    return (
        <UserContext.Provider value={{ getUserData, updateUserData, userId, updateUserId }}>
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
