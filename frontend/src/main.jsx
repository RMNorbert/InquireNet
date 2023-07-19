import "./index.css";
import App from "./App";
import React from "react";
import ReactDOM from "react-dom/client";

import { Home } from "./components/Home.jsx";
import { User } from "./components/user/User";
import { DocumentationPage } from "./components/DocumentationPage.jsx";
import { Login } from "./components/user/Login";
import { Navbar } from "./components/Navbar.jsx";
import { Chat } from "./components/chat/Chat.jsx";
import { Register } from "./components/user/Register";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { SelectedAnswer } from "./components/answers/SelectedAnswer.jsx";
import { SelectedQuestion } from "./components/questions/SelectedQuestion";
import { CreateQuestion } from "./components/questions/CreateQuestion.jsx";
import {EmployeePage} from "./components/user/EmployeePage.jsx";
import {UpdatePage} from "./components/Update.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login />,
    },
    {
        path: "/home",
        element: <Home />,
    },
    {
        path:"/docs",
        element:<DocumentationPage />,
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
    {
        path: "/employee",
        element: <EmployeePage />,
    },
    {
        path: "/question/update/:id",
        element: <UpdatePage />,
    },
    {
        path: "/answer/update/:id",
        element: <UpdatePage />,
    },
    {
        path: "/reply/update/:id",
        element: <UpdatePage />,
    }
]);

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <div>
            <Navbar />
            <RouterProvider router={router} />
        </div>
    </React.StrictMode>
);

