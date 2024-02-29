import '../../../assets/styles/auth.css'
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import { Link, useNavigate} from 'react-router-dom';
import Logo from '../../../components/logo';
import AuthService from '../../../api-service/authService';

function Login() {
    const navigate = useNavigate();

    const {register, handleSubmit,formState} = useForm();

    const [response_error, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (data) => {
        setIsLoading(true)        
        const response = await AuthService.login_req(data.email, data.password).then(
            (response) => {
                if (response.data.token) {
                    setResponseError("");
                    navigate("/");
                    window.location.reload();
                }
                else {
                    setResponseError("Login failed: Something went wrong!")
                }

            },
            (error) => {
                const resMessage =(error.response && error.response.data && error.response.data.message) || error.message || error.toString();
                console.log(resMessage);
                if (resMessage == "Bad credentials"){
                    setResponseError("Invalid email or password!");
                }else {
                    setResponseError("Something went wrong: Try again later!");
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
                    <h2>Login</h2>
                    {
                        (response_error!=="") && <p>{response_error}</p>
                    }
                    
                    <div className='input-box'>
                        <label>
                            Email
                            <input 
                                type='text'
                                {...register('email', {
                                    required: "Email is required!",
                                    pattern: {value:/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g, message:"Invalid email address!"}
                                })}
                            />
                        </label>
                        
                        {formState.errors.email && <small>{formState.errors.email.message}</small>}
                    </div>
                    
                    <div className='input-box'>
                        <label>
                            Password
                            <input 
                                type='password'
                                {
                                    ...register('password', {
                                        required: 'Password is required!',
                                    })
                                }
                            />
                        </label>
                        
                        {formState.errors.password && <small>{formState.errors.password.message}</small>}
                    </div>
                    <div className='msg'> <Link to={'/auth/forgotPassword/verifyEmail'} className='auth-link'>Forgot password?</Link></div>
                    
                    <div className='input-box'>
                        <input type='submit' value={isLoading ? 'Logging in' : 'Login'} className={isLoading ? 'loading' : ''}/>
                    </div>
                    <div className='msg'>New member? <Link to='/auth/register' className='auth-link'>Register Here</Link></div>
                </form>
            </div>
        </main>
    )
}

export default Login;