import React from 'react'
import Logo from '../../components/logo';
import { useNavigate } from 'react-router-dom';
import success from "../../assets/images/icons/success.gif"
import AuthService from '../../api-service/authService';

function OrderSuccessful() {

  const navigate = useNavigate()

  return (
    <main className='auth-container'>
      <div className='auth-wrapper' style={{ flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: '10px', gap: '20px' }}>
        <Logo />
        <img src={success} size='20px'/>
        <h3 style={{ textAlign: 'center' }}>
            Thank you for your order!<br/>
            Your order has been successfully placed.
            Order confirmation mail has been sent to {AuthService.getCurrentUser().email}.
            We will let you know once your parcel is on its way.
        </h3>
        <button style={{ width: '180px' }}
          onClick={() => navigate("/")}
        >Back to home</button>
      </div>
    </main>
  )
}

export default OrderSuccessful;