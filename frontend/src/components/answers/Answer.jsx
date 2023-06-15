import React from "react";

export const Answer = ({ description, created }) => {
    return (
        <div className="flex gap-3 bg-white text-black rounded-lg my-5 w-2/3 p-6 flex-col">
            <div className="gap-4">{created}</div>
            <div className="text-4xl">{description}</div>
        </div>
    );
};
