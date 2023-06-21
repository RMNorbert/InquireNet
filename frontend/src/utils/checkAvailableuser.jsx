export const checkAvailableUser = async (username, password) => {
    const response = await fetch("/api/user/login", {
        method: "PUT",
        body: JSON.stringify({username, password}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
};
