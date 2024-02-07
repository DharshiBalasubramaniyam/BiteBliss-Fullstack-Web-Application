import API_BASE_URL from "./apiConfig";
import axios from 'axios';

const register_req = async (username, email, password) => {
    return await axios.post(API_BASE_URL + '/auth/signup', {
        userName: username, 
        email: email, 
        password: password
    })
}

const login_req = async (email, password) => {
    const response = await axios.post(API_BASE_URL + '/auth/signin', {email, password})

    if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
    }

    return response;
}

const verifyVerificationCode = async (verificationCode) => {
    return await axios.get(API_BASE_URL + '/auth/signup/verify', {
        params: {
            code: verificationCode
        }
    })
}

const resendVerificationCode = async(email) => {
    return await axios.get(API_BASE_URL + "/auth/signup/resend", {
        params: {
            email: email
        }
    })
}

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const logout_req = () => {
    localStorage.removeItem("user");
}
const AuthService = {
    register_req,
    login_req,
    verifyVerificationCode,
    resendVerificationCode,
    getCurrentUser,
    logout_req
}


export default AuthService;