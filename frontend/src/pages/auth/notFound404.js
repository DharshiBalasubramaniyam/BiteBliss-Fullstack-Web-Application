import React from 'react'
import Logo from '../../components/logo';
import { useNavigate } from 'react-router-dom';

function NotFound404() {

  const navigate = useNavigate()
  return (
    <main className='auth-container'>
      <div className='auth-wrapper' style={{ flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: '10px', gap: '20px' }}>
        <Logo />
        <h1>404 - Not Found!</h1>
        <h3 style={{ textAlign: 'center' }}>Sorry! the page your are looking for was either not found or does not exist. Try refreshing the page or click the button below to go back to the home page.</h3>
        <button style={{ width: '180px' }}
          onClick={() => navigate("/")}
        >Go to home</button>
      </div>
    </main>
  )
}

export default NotFound404;