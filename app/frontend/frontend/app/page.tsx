"use client";

import CustomInput from "@/components/Layout/CustomInput";
import React, {useEffect, useState} from "react";
import useLazyGet from "@/hooks/API/useLazyGet";
import CustomButton from "@/components/Button/CustomButton";
import {useUser} from "@/userContext";
import {useRouter} from "next/navigation";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import {UserData} from "@/config/interfaces";

export default function Home() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    // Context
    const {updateUserData, updateUserId, resetLocalStorage, updateUserPassword} = useUser();
    const router = useRouter();

    const {
        error : userError,
        loading : userLoading,
        fetchData : fetchUser,
        data : userData
    } = useLazyGet<UserData>();

    function resetForm() {
        setEmail("");
        setPassword("");
    }

    async function handleSubmit() {
        if (!email || !password) {
            alert("Email and password are required");
            return;
        }


        try {
            await fetchUser("/auth/login", {username: email, password: password}, {});
            if (!userError) {
                console.log("User data fetched", userData);
            } else {
                new Error("Error logging in");
            }
        } catch (err) {
            alert("Oops, something went wrong");
            console.error("Unexpected error:", err);
            console.error("Error details:", userData, userError);
            resetForm();
        }
    }

    useEffect(() => {
        if (userData) {
            updateUserData(userData);
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-expect-error
            updateUserId(userData.id);
            updateUserPassword(password);
            console.log("User data fetched", userData);
            router.push("/dashboard");
        } else {
            console.log("No user data to fetch");
            resetForm();
        }
    }, [userData]);

    useEffect(() => {
        resetLocalStorage();
    }, []);

    return (
        <section className="bg-gray-50 dark:bg-gray-900">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                {(userLoading) ? (
                    <LoadingFullPage/>
                ) : (
                    <div
                        className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                                Login
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
                                <CustomButton type={"button"} onClick={handleSubmit}>
                                    Sign in
                                </CustomButton>
                                {(userError) && (
                                    <div
                                        className={"text-red-500 dark:text-red-400 bg-red-100 dark:bg-red-700 p-2 text-sm text-center rounded"}>
                                        <p>{userError}</p>
                                    </div>
                                )}
                                <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                                    Don’t have an account yet? <a href="/register"
                                                                  className="font-medium text-primary-600 hover:underline dark:text-primary-500">Sign
                                    up</a>
                                </p>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </section>
    );
}