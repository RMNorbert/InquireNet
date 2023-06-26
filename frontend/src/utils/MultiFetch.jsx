import { API_KEY } from "../Config";
import { token } from "./TokenDecoder.jsx";
export const multiFetch = async (url, method, data ) => {
    const tokenValue = token();
    const headers = tokenValue ?
        {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${tokenValue}`,
        } :
        {
            "Content-Type": "application/json",
        };
    const response = await fetch(url, {
        method: method,
        body: JSON.stringify(data),
        headers: headers
    });
    if (!response.ok) {
        const errorResponse = await response.json();
        console.log(errorResponse.message || "An error occurred");
        throw new Error(errorResponse.message);
    }
    return await response.json();
}

export const authFetch = async (url, data, login) => {
    const response = await fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data),
    })
     if(response.ok){
        if(login) {
            const data = await response.json();
            const token = data.token;
            const time = data.time;
            localStorage.setItem('token', token);
            localStorage.setItem('time', time);
            }
    } else {
        return await response.text();
    }
}
export const aiFetch = async (url, method, title) => {
    const response = await fetch(url, {
        method: method,
        body: JSON.stringify({
                model : "gpt-3.5-turbo",
                messages: [{ role: "user", content: title}],
                max_tokens : 200,
            }
        ),
        headers: {
            "Authorization": `Bearer ${API_KEY}`,
            "Content-Type": "application/json"
        },
    });
    return await response.json();
}
