import '../../../assets/styles/auth.css'
import { useState, useRef} from 'react';
import {useForm} from 'react-hook-form';
import { useNavigate, useParams} from 'react-router-dom';
import AuthService from '../../../api-service/authService';
import Logo from '../../../components/logo';

function ResetPassword() {
    const navigate = useNavigate();

    const {register, handleSubmit,formState, watch} = useForm();
    const password = useRef({});
    password.current = watch('password', "");

    const [response_error, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const { email } = useParams(); 

    const onSubmit = async (data) => {
        setIsLoading(true);     
        const response = await AuthService.resetPassword(email, data.password).then(
            (response) => {
                console.log(response);
                if (response.data.status === "SUCCESS"){
                    setResponseError("");
                    navigate(`/auth/forgotPassword/success`);
                }
                else {
                    setResponseError("Reset password failed: Something went wrong!")
                }
            },
            (error) => {
                if (error.response) {
                    const resMessage = error.response.data.response
                    setResponseError(resMessage);
                    console.log(error.response);
                }else {
                    setResponseError("Reset password failed: Something went wrong!")
                }
                
            }
          );
        setIsLoading(false);
    }


    return(
        <main className='auth-container'>
            <div className='auth-wrapper'>
                <div>
                </div>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <Logo/>
                    <h2>Reset your password</h2>
                    {
                        (response_error!=="") && <p>{response_error}</p>
                    }
                    
                    <div className='input-box'>
                        <label>
                            New password
                            <input 
                                type='password'
                                {
                                    ...register('password', {
                                        required: 'Password is required!',
                                        minLength: {
                                            value:8,
                                            message: "Password must have atleast 8 characters!"
                                        },
                                        maxLength: {
                                            value: 20,
                                            message: "Password cannot have more than 20 characters!"
                                        }
                                    })
                                }
                            />
                        </label>
                        
                        {formState.errors.password && <small>{formState.errors.password.message}</small>}
                    </div>

                    <div className='input-box'>
                        <label>
                            Confirm password
                            <input 
                                type='password'
                                {
                                    ...register('cpassword', {
                                        required: 'Confirm password is required!',
                                        minLength: {
                                            value:8,
                                            message: "Password must have atleast 8 characters!"
                                        },
                                        maxLength: {
                                            value: 20,
                                            message: "Password cannot have more than 20 characters!"
                                        },
                                        validate: cpass => cpass === password.current || "Passwords do not match!"
                                    })
                                }
                            />
                        </label>
                        {formState.errors.cpassword && <small>{formState.errors.cpassword.message}</small>}
                    </div>

                    
                    <div className='input-box'>
                        <input type='submit' value={isLoading ? 'Resetting...' : 'Reset password'} className={isLoading ? 'loading' : ''}/>
                    </div>
                </form>
            </div>
        </main>
    )
}

export default ResetPassword;