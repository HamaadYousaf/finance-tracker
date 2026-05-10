const BASE_URL = "http://localhost:8080/api";

// Generic request helper
const request = async (url, options = {}) => {
    const res = await fetch(url, {
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
        },
        ...options,
    });

    if (!res.ok) {
        const errorText = await res.text();
        throw new Error(errorText || "Request failed");
    }

    // Handle empty responses
    if (res.status === 204) return null;

    return res.json();
};

//
// 📊 DASHBOARD
//
export const getDashboard = (userId) => {
    return request(`${BASE_URL}/dashboard/${userId}`);
};

//
// 💰 EXPENSES
//
export const getExpenses = (userId) => {
    return request(`${BASE_URL}/expenses/${userId}`);
};

export const addExpense = (expense) => {
    return request(`${BASE_URL}/expenses`, {
        method: "POST",
        body: JSON.stringify(expense),
    });
};

export const deleteExpense = (id) => {
    return request(`${BASE_URL}/expenses/${id}`, {
        method: "DELETE",
    });
};

//
// 💳 SUBSCRIPTIONS
//
export const getSubscriptions = (userId) => {
    return request(`${BASE_URL}/subscriptions/${userId}`);
};

export const addSubscription = (subscription) => {
    return request(`${BASE_URL}/subscriptions`, {
        method: "POST",
        body: JSON.stringify(subscription),
    });
};

export const updateSubscription = (id, subscription) => {
    return request(`${BASE_URL}/subscriptions/${id}`, {
        method: "PUT",
        body: JSON.stringify(subscription),
    });
};

export const deleteSubscription = (id) => {
    return request(`${BASE_URL}/subscriptions/${id}`, {
        method: "DELETE",
    });
};