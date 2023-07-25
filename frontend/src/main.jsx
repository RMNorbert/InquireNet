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
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { SelectedAnswer } from "./components/answers/SelectedAnswer.jsx";
import { SelectedQuestion } from "./components/questions/SelectedQuestion";
import { CreateQuestion } from "./components/questions/CreateQuestion.jsx";
import {EmployeePage} from "./components/user/EmployeePage.jsx";
import {UpdatePage} from "./components/Update.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <Router>
            <Navbar/>
            <Routes>
                <Route path='/' element={ <Login />} />
                <Route path='/register' element={<Register/>}/>
                <Route path='/home' element={<Home />} />
                <Route path='/docs' element={<DocumentationPage/>} />
                <Route path='/chat' element={<Chat/>}/>
                <Route path='/forum' element={<App/>}/>
                <Route path='/question' element={<App/>}/>
                <Route path='/question/:id' element={<SelectedQuestion/>}/>
                <Route path='/answer/:id' element={<SelectedAnswer/>}/>
                <Route path='/createquestion' element={<CreateQuestion/>}/>
                <Route path='/user' element={<User/>}/>
                <Route path='/employee' element={<EmployeePage/>}/>
                <Route path='/question/update/:id' element={<UpdatePage/>} />
                <Route path='/answer/update/:id' element={<UpdatePage/>} />
                <Route path='/reply/update/:id' element={<UpdatePage/>} />
            </Routes>
        </Router>
    </React.StrictMode>
    );

