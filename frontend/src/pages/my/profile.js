import { Link } from 'react-router-dom';
import '../../assets/styles/user.css';
import Header from '../../components/header';
import { useForm } from 'react-hook-form';
import { useEffect, useRef, useState } from 'react';
import Footer from '../../components/footer';
import UserHeader from '../../components/userHeader';
import AuthService from '../../api-service/authService';

function Profile() {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setEmail(user.email)
            setUsername(user.username)
        }
    }, [])
    return (
        <>
            <UserHeader />
            <div className='user'>
                <h1>Hello John!</h1>
                <h2>My profile</h2>
                <form>
                    <div className='input-box'>
                        <label>Username</label><br />
                        <input type='text' value={username} disabled/>
                    </div>
                    <div className='input-box'>
                        <label>Email</label><br />
                        <input type='text' value={email} disabled/>
                    </div>
                </form>

                <Link to='/my/profile/changepassword' className='nav-link'>Change password</Link>
            </div>
            <Footer/>
        </>

    )

}

export default Profile;