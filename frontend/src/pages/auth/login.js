import '../../assets/styles/auth.css'
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import { Link, useNavigate} from 'react-router-dom';
import Logo from '../../components/logo';
import AuthService from '../../api-service/authService';

function Login() {
    const navigate = useNavigate();

    const {register, handleSubmit,formState} = useForm();

    const [response_error, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (data) => {
        console.log(data);
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
                if (error.response) {
                    const resMessage = error.response.data.message || error.response.data
                    if (resMessage === "Bad credentials"){
                        setResponseError("Incorrect email or password!");
                    }else {
                        setResponseError(resMessage);
                    }
                }else {
                    setResponseError("Login failed: Some thing went wrong!");
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
                    <div className='msg'> <Link to={'/auth/forgetpassword/verifyEmail'} className='auth-link'>Forgot password?</Link></div>
                    
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