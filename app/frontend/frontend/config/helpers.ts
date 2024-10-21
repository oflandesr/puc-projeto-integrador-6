// Utility function to create Basic Auth header
const createBasicAuthHeader = (username: string, password: string) => {
    const token = btoa(`${username}:${password}`);
    return `Basic ${token}`;
};

export { createBasicAuthHeader };