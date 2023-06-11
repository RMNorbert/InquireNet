import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./index.css";

import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { Register } from "./components/user/Register";
import { Login } from "./components/user/Login";
import { SelectedQuestion } from "./components/questions/SelectedQuestion";
import { CreateQuestion } from "./components/questions/CreateQuestion";
import { Header } from "./components/Header";
import { User } from "./components/user/User";

const router = createBrowserRouter([
    {
        path: "/",
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
            <Header />
            <RouterProvider router={router} />
        </div>
    </React.StrictMode>
);

