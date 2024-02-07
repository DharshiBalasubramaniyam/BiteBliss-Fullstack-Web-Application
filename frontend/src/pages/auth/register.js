import '../../assets/styles/auth.css'
import {useEffect, useState, useRef} from 'react';
import {useForm} from 'react-hook-form';
import { Link, useNavigate} from 'react-router-dom';
import Logo from '../../components/logo';
import AuthService from '../../api-service/authService';


function Register() {
    const navigate = useNavigate();

    const {register, handleSubmit,formState, watch} = useForm();
    const password = useRef({});
    password.current = watch('password', "");

    const [response_error, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (data) => {
        setIsLoading(true);     
        const response = await AuthService.register_req(data.username, data.email, data.password).then(
            (response) => {
                console.log(response);
                if (response.data.status === "SUCCESS"){
                    setResponseError("");
                    navigate(`/auth/userRegistrationVerfication/${data.email}`);
                }
                else {
                    setResponseError("Registration failed: Something went wrong!")
                }
            },
            (error) => {
                if (error.response) {
                    const resMessage = error.response.data.error
                    setResponseError(resMessage);
                    console.log(error.response);
                }else {
                    setResponseError("Registration failed: Something went wrong!")
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
                    <h2>Register</h2>
                    {
                        (response_error!=="") && <p>{response_error}</p>
                    }

                    <div className='input-box'>
                        <label>
                            <input 
                                type='text'
                                placeholder='Username'
                                {...register('username', {
                                    required: "Username is required!",
                                    maxLength: {
                                        value: 20,
                                        message: "Username cannot have more than 20 characters!"
                                    },
                                    minLength: {
                                        value: 3,
                                        message: "Username must have atleast 20 characters!"
                                    }
                                })}
                            />
                        </label>
                        
                        {formState.errors.username && <small>{formState.errors.username.message}</small>}
                    </div>
                    
                    
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
                        <label>
                            <input 
                                type='password'
                                placeholder='Password'
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
                        <input 
                            type='password'
                            placeholder='Confirm password'
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
                        {formState.errors.cpassword && <small>{formState.errors.cpassword.message}</small>}
                    </div>

                    
                    <div className='input-box'>
                        <input type='submit' value={isLoading ? 'Registering' : 'Register'} className={isLoading ? 'loading' : ''}/>
                    </div>
                    <div className='msg'>By clicking Register, you are agree to our user agreement, privacy policy, and cookie policy.</div>
                    <div className='msg'>Already a member? <Link to='/auth/login' className='auth-link'>Login Here</Link></div>
                </form>
            </div>
        </main>
    )
}

export default Register;