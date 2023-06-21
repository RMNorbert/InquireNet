export const createUser =async(username, password)=>{
    const response = await fetch("/api/user/create ", {
        method: "POST",
        body: JSON.stringify({username, password}),
        mode: "cors",
        headers: {"Content-Type": "application/json"},
    });
    return await response.json();

}
