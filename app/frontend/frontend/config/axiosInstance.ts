"use client";

import axios from 'axios';

// Encode the username and password in base64
const username = 'admin';
const password = 'admin';
const token = Buffer.from(`${username}:${password}`, 'utf8').toString('base64');

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api', // Using a public CORS proxy
    timeout: 10000
    /*
    headers: {
        'Authorization': `Basic ${token}`,
    },
    */
});

// Request interceptor to log the full URL
axiosInstance.interceptors.request.use(request => {
    // Construct the full URL
    const fullURL = `${request.baseURL}${request.url}`;
    console.log(`Request URL: ${fullURL}`); // Log the full URL
    return request;
});

export default axiosInstance;
