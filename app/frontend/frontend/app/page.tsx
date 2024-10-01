"use client";

import CustomInput from "@/components/CustomInput";
import {useEffect, useState} from "react";
import useLazyGet from "@/hooks/useLazyGet";
import CustomButton from "@/components/CustomButton";
import { Bars } from 'react-loading-icons'

export default function Home() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const {
        data,
        error,
        loading,
        fetchData
    } = useLazyGet("/auth/login", { email, password });

    async function handleSubmit() {
        await fetchData();
    }

    useEffect(() => {
        console.log("Data: ", data);
        // If the data changes to
    }, [data]);

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <section className="bg-gray-50 dark:bg-gray-900">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                { loading ? (
                    <div className="flex justify-center items-center h-full">
                        <Bars className="w-10 h-10 text-blue-600" />
                    </div>
                ) : (
                    <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                                Sign in to your account
                            </h1>
                            <div className="space-y-4 md:space-y-6">
                                <CustomInput
                                    id={"email"}
                                    name={"email"}
                                    type={"email"}
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder={"Enter your email..."}
                                />
                                <CustomInput
                                    id={"password"}
                                    name={"password"}
                                    type={"password"}
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    placeholder={"Enter your password..."}
                                />
                                <CustomButton type={"submit"} onClick={handleSubmit}>
                                    Sign in
                                </CustomButton>
                                <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                                    Don’t have an account yet? <a href="#" className="font-medium text-primary-600 hover:underline dark:text-primary-500">Sign up</a>
                                </p>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </section>
    );
}
