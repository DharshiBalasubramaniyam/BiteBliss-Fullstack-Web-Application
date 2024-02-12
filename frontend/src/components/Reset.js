import React from 'react'
import { Toaster } from 'react-hot-toast';
import { useFormik } from 'formik';
import { resetPasswordValidation } from '../helper/validate';


import styles from '../assets/styles/Username.module.css';

export default function Reset() {

 

  const formik = useFormik({
    initialValues : {
      password : '',
      confirm_pwd:''
    },
    validate : resetPasswordValidation,
    validateOnBlur: false,
    validateOnChange: false,
    onSubmit : async values => {
     console.log(values);
    }
  })

  return (
    <div className="container">

      <Toaster position='top-center' reverseOrder={false}></Toaster>

      <div className='container_1'>
        <div className={styles.glass}>

          <div className="title">
            <h4>Reset</h4> 
            <span className='text' style={{marginLeft:'110px'}}>
                Enter new password.
            </span>
          </div>

          <form className='forms' onSubmit={formik.handleSubmit}>


              <div className="textbox">
                  <input {...formik.getFieldProps('password')} className={styles.textbox} type="text" placeholder='Password' />
                  <input {...formik.getFieldProps('confirm_pwd')} className={styles.textbox} type="text" placeholder='Repeat Password' />
                  <button className={styles.btn} type='submit'>Sign In</button>
              </div>


          </form>

        </div>
      </div>
    </div>
  )
}