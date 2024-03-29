import { Link } from "react-router-dom";
import Logo from "../../../components/logo";
import success from "../../../assets/images/icons/success.gif"

function ResetPasswordSuccessful() {
    return(
        <main className='auth-container'>
            <div className='auth-wrapper'>
                <div></div>
                <form style={{alignItems:'center'}}>
                    <Logo/>
                    <img src={success} size='20px'/>
                    <h4 style={{textAlign:"center", color: "green"}}>Congratulations, Your password has been successfully changed!</h4>
                    <div className="input-box">
                        <Link to='/auth/login'><button className="button button-fill">Login now</button></Link>
                    </div>
                </form>
            </div>
        </main>
    )
}

export default ResetPasswordSuccessful;