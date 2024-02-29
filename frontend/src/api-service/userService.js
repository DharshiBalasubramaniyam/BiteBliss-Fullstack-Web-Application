import API_BASE_URL from "./apiConfig";
import axios from 'axios';
import AuthService from "./authService";

const resetPassword = async (email, currentPassword, newPassword) => {
    console.log(AuthService.authHeader())

    return await axios.post(API_BASE_URL + '/user/changePassword', {
        email: email, 
        currentPassword: currentPassword,
        newPassword: newPassword
    }, {
        headers: AuthService.authHeader()
    })

}

const UserService = {
    resetPassword
}

export default UserService;