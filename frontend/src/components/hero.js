import { useEffect, useState } from 'react';
import '../assets/styles/hero.css'
import {Link} from 'react-router-dom'
import AuthService from '../api-service/authService';

function Hero() {

    const [user, setUser] = useState(false);

    useEffect(()=> {
        const u = AuthService.getCurrentUser();

        if (u) {
            setUser(true);
            console.log(user);
        }
    }, [])
    return(
        <section className="hero-section" id='hero'>
            {
                (user) ? <h4>Wecome back, {AuthService.getCurrentUser().username}!</h4> : ""
            }
            <h3>Relish Blissful Tastes at BiteBliss,</h3>

            <h1>Where every bite is crafted and every moment is a culinary delight!</h1>
            <div>
                <button>Order now</button>
                {
                    (!user) ? <Link to='/auth/register'> <button className='outline'>Register</button></Link> : <></>
                }
                
            </div>
        </section>
    )
}

export default Hero;