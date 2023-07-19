import 'swagger-ui-dist/swagger-ui.css';
import SwaggerUI from 'swagger-ui-dist/swagger-ui-bundle.js';
import 'swagger-ui-dist/swagger-ui-standalone-preset.js';

import React, { useEffect } from 'react';
import {username} from "../utils/TokenDecoder.jsx";
import {useNavigate} from "react-router-dom";

export const DocumentationPage = () => {
    const navigate = useNavigate();
    useEffect(() => {
        if(username() === null){
            navigate("/");
        }
        const swaggerUrl = '/api/api-docs';
        SwaggerUI({
            url: swaggerUrl,
            dom_id: '#swagger-ui',
        });
    }, []);

    return <div id="swagger-ui"></div>;
};

