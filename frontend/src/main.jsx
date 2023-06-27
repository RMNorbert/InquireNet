import "./index.css";
import App from "./App";
import React from "react";
import ReactDOM from "react-dom/client";

import { Home } from "./components/Home.jsx";
import { User } from "./components/user/User";
import { Login } from "./components/user/Login";
import { Navbar } from "./components/Navbar.jsx";
import { Chat } from "./components/chat/Chat.jsx";
import { Register } from "./components/user/Register";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { SelectedAnswer } from "./components/answers/SelectedAnswer.jsx";
import { SelectedQuestion } from "./components/questions/SelectedQuestion";
import { CreateQuestion } from "./components/questions/CreateQuestion.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Home />,
    },
    {
        path: "/chat",
        element: <Chat />,
    },
    {
        path: "/forum",
        element: <App />,
    },
    {
        path: "/question",
        element: <App />,
    },
    {
        path: "/register",
        element: <Register />,
    },
    {
        path: "/login",
        element: <Login />,
    },
    {
        path: "/question/:id",
        element: <SelectedQuestion />,
    },
    {
        path: "/answer/:id",
        element: <SelectedAnswer />,
    },
    {
        path: "/createquestion",
        element: <CreateQuestion />,
    },
    {
        path: "/user",
        element: <User />,
    },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <div>
            <Navbar />
            <RouterProvider router={router} />
        </div>
    </React.StrictMode>
);

