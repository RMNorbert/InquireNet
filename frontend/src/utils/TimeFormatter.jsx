export const createdTime = (created) => {
    return new Date(created)
        .toISOString()
        .substring(0, 19)
        .replace("T", " ");
}
