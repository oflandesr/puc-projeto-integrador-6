// Utility function to create Basic Auth header
const createBasicAuthHeader = (username: string, password: string) => {
    const token = btoa(`${username}:${password}`);
    return `Basic ${token}`;
};

const formatDate = (stringDate: string) => {
    const date = new Date(stringDate);
    return date.toLocaleDateString();
}

export { createBasicAuthHeader, formatDate };