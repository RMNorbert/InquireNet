export const checkAvailableUser = async (username, password) => {
    const response = await fetch("http://127.0.0.1:8080/user/login", {
        method: "PUT",
        body: JSON.stringify({username, password}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();
};
