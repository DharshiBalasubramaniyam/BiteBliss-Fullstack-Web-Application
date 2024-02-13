import '../assets/styles/header.css';
import { Link, useNavigate} from 'react-router-dom';
import { useEffect, useState } from 'react';
import AuthService from '../api-service/authService';
import AuthVerify from '../api-service/authVerify';
import Logo from './logo'

function Header() {


    const [userMode, setUserMode] = useState(false);
    const [isNavOpen, setIsNavOpen] = useState(false);

    const handleNav = () => {
        setIsNavOpen(!isNavOpen);
    }

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setUserMode(true);
        }else {
            setUserMode(false)
        }
    }, [])

    const navigate = useNavigate();
 
    const logout = () => {
        AuthService.logout_req();
        navigate('/');
        window.location.reload();
    }

    return (
        <header className="app-header">
            <div className='logo-wrapper'>
                <span>
                    {
                        isNavOpen ? <i class="fa fa-times" aria-hidden="true" onClick={handleNav}></i> :
                            <i class="fa fa-bars" aria-hidden="true"  onClick={handleNav}></i> 
                    }
                </span>
                <span><Link to='/'><Logo/></Link></span>
            </div>
            <ul className = {(isNavOpen) ? 'nav-open' : 'nav-close' }>
                <li><a href='#hero' className='nav-link'>Home</a></li>
                <li><Link to='/' className='nav-link'>Shop</Link></li>
                {!userMode && <li><Link to='/auth/login' className='nav-link'>Login</Link></li> }
                {/* in future replace this with account dropdown -> Orders, Profile, Log out */}
                {userMode && <li onClick={logout} className='nav-link'><Link className='nav-link'>Logout</Link></li> } 
            </ul>
            <ul>
                <li><Link to='/'><i class="fa fa-search" aria-hidden="true"></i></Link></li>
                <li><Link to='/'><i class="fa fa-shopping-cart" aria-hidden="true"></i><span>(0)</span></Link></li>
            </ul>
            <AuthVerify logOut={logout}/>
        </header>
    );
    
}
export default Header;