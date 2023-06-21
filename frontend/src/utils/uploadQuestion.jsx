export const uploadQuestion = async(title, description, userID)=>{

    const response = await fetch("/api/questions/", {
        method: "POST",
        body: JSON.stringify({
            title,
            description,
            userID,
        }),
        mode: "cors",
        headers: { "Content-Type": "application/json" },
    });
    const data = await response.json();
}
