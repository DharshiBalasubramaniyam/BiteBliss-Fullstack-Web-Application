import React, { useState, useEffect } from 'react';
import { useNavigate, useParams, Link } from 'react-router-dom';
import {useForm} from 'react-hook-form';
import Logo from '../../components/logo';
import AuthService from '../../api-service/authService';

function RegistrationVerfication() {
    const navigate = useNavigate();

    const { email } = useParams(); 

    const {register, handleSubmit, formState} = useForm();

    const [response_error, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const [isSending, setIsSending] = useState(false);


    const onSubmit = async (data) => {
        setIsLoading(true) 
        setResponseError("")       
        const response = await AuthService.verifyVerificationCode(data.code).then(
            (response) => {
                console.log(response.data.message);
                if (response.data.status === 'SUCCESS') {
                    setResponseError("")
                    navigate("/auth/success-registration");
                } else {
                    setResponseError('Verification failed: Something went wrong!');
                }
            },
            (error) => {
                if (error.response) {
                    const resMessage = error.response.data.error;
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

    const resendCode = async() =>{
        setResponseError("")
        setIsSending(true)        
        const response = await AuthService.resendVerificationCode(email).then(
            (response) => {
                console.log(response.data);
                if (response.data.status === "SUCCESS") {
                    console.log(response.data);
                    setResponseError("");
                }else {
                    setResponseError("Verification failed: Cannot resend email!")
                }
                
            },
            (error) => {
                if (error.response) {
                    const resMessage = error.response.data.error;
                    setResponseError(resMessage);
                    console.log(error);
                }else {
                    console.log(error);
                    setResponseError("Verification failed: Cannot resend email!")
                }
            }
          );
        setIsSending(false);
    }


    return(
        <main className='auth-container'>
            <div className='auth-wrapper'>
                <div>
                </div>
                <form onSubmit={handleSubmit(onSubmit)} style={{gap:'15px'}}>
                    <Logo/>
                    <h2>Verify your email</h2>

                    {
                        isSending ? <div className='msg'>Sending email to {email}...</div> : <div className='msg'>The verification code has been sent to <span style={{fontWeight:600,  color:'green'}}>{email}</span>.</div>
                    }

                    {
                        (response_error!=="") && <p>{response_error}</p>
                    }

                    <div className='input-box'>
                        <label>
                            <input 
                                placeholder='Enter verification code'
                                    type='text'
                                    {...register('code', {
                                        required: "Verification code is required!",
                                    })}
                            />
                        </label>
                        
                        {formState.errors.code && <small>{formState.errors.code.message}</small>}
                    </div>
                    
                    <div className='msg' style={{fontWeight: 600, fontStyle: 'italic'}}>Please not that the verification code will be expired with in 15 minutes!</div>
                    
                    <div className='input-box'>
                        <input type='submit' value={isLoading ? 'Verifying' : 'Verify'} className={isLoading ? 'loading' : ''}/>
                    </div>

                    <div className='msg'>Having problems? <span className='auth-link' onClick={resendCode}>Resend code</span></div>
                </form>
            </div>
        </main>
    )
}

export default RegistrationVerfication;