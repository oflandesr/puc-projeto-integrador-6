"use client";

import CustomInput from "@/components/Layout/CustomInput";
import React, { useState} from "react";
import CustomButton from "@/components/Button/CustomButton";
import {useRouter} from "next/navigation";
import usePost from "@/hooks/Api/usePost";
import LoadingFullPage from "@/components/Layout/LoadingFullPage";
import {UserData} from "@/config/interfaces";

// Define the payload type (U)
interface CreateUserPayload {
    login: string;
    password: string;
    firstName: string;
    lastName: string;
}


export default function Home() {

    const [fname, setFname] = useState("");
    const [lname, setLname] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const router = useRouter();

    const {
        error,
        loading,
        postData
    } = usePost<UserData, CreateUserPayload>();

    function resetForm() {
        setUsername("");
        setPassword("");
        setFname("");
        setLname("");
    }

    async function handleSubmit() {
        if (!username || !password || !fname || !lname) {
            alert("Email and password are required");
            return;
        }

        const payload : CreateUserPayload = {
            login: username,
            password: password,
            firstName: fname,
            lastName: lname
        }

        await postData("/user", payload, {username: "admin", password: "admin"});

        if (!error) {
            alert("Register successful");
            router.push("/");
        } else {
            console.log("Oops, something went wrong", error);
            resetForm();
        }
    }

    return (
        <section className="bg-gray-50 dark:bg-gray-900">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
                { (loading) ? (
                    <LoadingFullPage />
                ) : (
                    <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                            <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                                Register your account
                            </h1>
                            <div className="space-y-4 md:space-y-6">
                                <CustomInput
                                    id={"username"}
                                    name={"username"}
                                    type={"username"}
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    placeholder={"Enter your username..."}
                                />
                                <CustomInput
                                    id={"password"}
                                    name={"password"}
                                    type={"password"}
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    placeholder={"Enter your password..."}
                                />
                                <CustomInput id={"fname"} name={"fname"} type={"text"} value={fname} onChange={(e) => setFname(e.target.value)} placeholder={"Enter your first name..."} />
                                <CustomInput id={"lname"} name={"lname"} type={"text"} value={lname} onChange={(e) => setLname(e.target.value)} placeholder={"Enter your last name..."} />
                                <CustomButton type={"button"} onClick={handleSubmit}>
                                    Register
                                </CustomButton>
                                { error && (
                                    <div className={"text-red-500 dark:text-red-400 bg-red-100 dark:bg-red-700 p-2 text-sm text-center rounded"}>
                                        <p>{error}</p>
                                    </div>
                                )}
                                <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                                    Have an account? <a href="/" className="font-medium text-primary-600 hover:underline dark:text-primary-500">Log In</a>
                                </p>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </section>
    );
}