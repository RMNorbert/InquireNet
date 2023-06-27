import jwt_decode from 'jwt-decode';

export const username = () => {
    try{
        const token = localStorage.getItem('token');
        const decodedToken = jwt_decode(token);
        return decodedToken.sub;
    } catch (error) {
        return null;
    }
}

export const role = () => {
    try {
        const token = localStorage.getItem('token');
        const decodedToken = jwt_decode(token);
        return decodedToken.role;
    } catch (error) {
        return null;
    }

}

export const loggedInUserId = () => {
    try {
        const token = localStorage.getItem('token');
        const decodedToken = jwt_decode(token);
        return decodedToken.userId;
    } catch (error) {
        return null;
    }

}
export const token = () => {
    try {
        return localStorage.getItem('token');
    } catch (error) {
        return null;
    }

}

export const time = () => {
    try {
        return localStorage.getItem('time');
    } catch (error) {
        return null;
    }

}
