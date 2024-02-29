import '../assets/styles/logo.css'
import {Link} from 'react-router-dom'

function Logo() {
    return (
        <Link to='/'><h1 className='logo'><span></span>BiteBliss</h1></Link>
    )
}

export default Logo;