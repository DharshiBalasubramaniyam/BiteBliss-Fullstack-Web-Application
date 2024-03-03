import { useRef, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import '../../assets/styles/user.css';
import Footer from "../../components/footer";
import UserHeader from "../../components/userHeader";
import UserService from "../../api-service/userService";
import AuthService from "../../api-service/authService";

function ChangePassword() {
    const { register, handleSubmit, watch, reset, formState } = useForm();
    const password = useRef({});
    password.current = watch('newPassword', "");
    const navigate = useNavigate()
    const [responseError, setResponseError] = useState(null);
    const [success, setSuccess] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    const onError = (err) => {
        setResponseError(err)
        setSuccess(null)
    }
    const onSubmit = async (data) => {
        console.log(data)
        setIsLoading(true);
        const response = await UserService.resetPassword(AuthService.getCurrentUser().email, data.currentPassword, data.newPassword).then(
            (response) => {
                console.log(response);
                if (response.data.status === "SUCCESS") {
                    setResponseError(null)
                    setSuccess(response.data.response)
                    reset()
                    return
                }
                onError("Failed to change password: Try again later!");
            },
            (error) => {
                console.log(error);
                error.response ?
                    onError(error.response.data.response)
                    :
                    onError("Failed to change password: Try again later!");
            }
        );
        setIsLoading(false);
    }
    return (
        <>
            <UserHeader />
            <div className='user'>
                <h1>Hello {AuthService.getCurrentUser().username}!</h1>
                <h2>Change password</h2>
                <form onSubmit={handleSubmit(onSubmit)}>
                    {responseError ? <p className="error">{responseError}</p> : <></>}
                    {success ? <p className="success">{success}</p> : <></>}
                    <div className='input-box'>
                        <label>Current Password</label><br />
                        <input
                            type='password'
                            {
                            ...register('currentPassword', {
                                required: 'Current password is required!',
                                minLength: {
                                    value: 8,
                                    message: "Password must have atleast 8 characters"
                                }
                            })
                            }
                        />
                        {formState.errors.currentPassword && <small>{formState.errors.currentPassword.message}</small>}
                    </div>

                    <div className='input-box'>
                        <label>Password</label><br />
                        <input
                            type='password'
                            {
                            ...register('newPassword', {
                                required: 'Password is required!',
                                minLength: {
                                    value: 8,
                                    message: "Password must have atleast 8 characters"
                                }
                            })
                            }
                        />
                        {formState.errors.newPassword && <small>{formState.errors.newPassword.message}</small>}
                    </div>

                    <div className='input-box'>
                        <label>Confirm Password</label><br />
                        <input
                            type='password'
                            {
                            ...register('cpassword', {
                                required: 'Confirm password is required!',
                                minLength: {
                                    value: 8,
                                    message: "Password must have atleast 8 characters"
                                },
                                validate: cpass => cpass === password.current || "Passwords do not match!"
                            })
                            }
                        />
                        {formState.errors.cpassword && <small>{formState.errors.cpassword.message}</small>}
                    </div>

                    <div className='input-box btn'>
                        <button
                            className={isLoading ? "loading" : ""}
                        >
                            {isLoading ? "Updating..." : 'Update password'}
                        </button>
                        <button className="outline" onClick={() => navigate('/my/profile')}>Cancel</button>
                    </div>
                </form>

            </div>
            <Footer/>
        </>
    )
}

export default ChangePassword;