import '../../../assets/styles/auth.css'
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import { Link, useNavigate} from 'react-router-dom';
import Logo from '../../../components/logo';
import AuthService from '../../../api-service/authService';

function ForgotPasswordEmailVerification() {
    const navigate = useNavigate();

    const {register, handleSubmit, formState} = useForm();

    const [response_error, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (data) => {
        console.log(data);
        setIsLoading(true)        
        const response = await AuthService.forgotPasswordVerifyEmail(data.email).then(
            (response) => {
                console.log(response.data.message);
                if (response.data.status === 'SUCCESS') {
                    setResponseError("")
                    navigate(`/auth/forgotPassword/verifyAccount/${data.email}`);
                } else {
                    setResponseError('Verification failed: Something went wrong!');
                }
            },
            (error) => {
                if (error.response) {
                    const resMessage = error.response.data.response;
                    setResponseError(resMessage);
                    console.log(resMessage);
                }else {
                    console.log(error.message);
                    setResponseError("Verification failed: Something went wrong!")
                }
            }
        );
        setIsLoading(false);
    }
    
    return(
        <main className='auth-container'>
            <div className='auth-wrapper'>
                <div></div>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <Logo/>
                    <h2>Verify your email</h2>
                    {
                        (response_error!=="") && <p>{response_error}</p>
                    }
                    
                    <div className='msg'>Enter the email address on your account to reset your password.</div>
                    <div className='input-box'>
                        <label>
                            <input 
                                type='text'
                                placeholder='Email address'
                                {...register('email', {
                                    required: "Email is required!",
                                    pattern: {value:/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g, message:"Invalid email address!"}
                                })}
                            />
                        </label>
                        
                        {formState.errors.email && <small>{formState.errors.email.message}</small>}
                    </div>
                    
                    
                    <div className='input-box'>
                        <input type='submit' value={isLoading ? 'Verifying...' : 'Verify'} className={isLoading ? 'loading' : ''}/>
                    </div>
                    <div className='msg'>Remember password? <Link to='/auth/login' className='auth-link'>Back to login</Link></div>
                </form>
            </div>
        </main>
    )
}

export default ForgotPasswordEmailVerification;