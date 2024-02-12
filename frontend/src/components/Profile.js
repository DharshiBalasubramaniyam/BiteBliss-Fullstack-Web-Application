import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import avatar from '../assets/images/profile.png';
import toast, { Toaster } from 'react-hot-toast';
import { useFormik } from 'formik';
import { profileValidation } from '../helper/validate';
import convertToBase64 from '../helper/convert';



import styles from '../assets/styles/Username.module.css';
import extend from '../assets/styles/Profile.module.css';

export default function Profile() {

  const navigate = useNavigate()
  const [file, setFile] = useState()

  const formik = useFormik({
    initialValues : {
      firstname:'',
      lastname:'',
      email: '',
      mobile: '',
      address : ''
    },
    validate : profileValidation,
    validateOnBlur: false,
    validateOnChange: false,
    onSubmit : async values => {
      values = await Object.assign(values, { profile : file || ''})
      console.log(values)
    }
  })

  /** formik doensn't support file upload so we need to create this handler */
  const onUpload = async e => {
    const base64 = await convertToBase64(e.target.files[0]);
    setFile(base64);
  }

  return (
    <div className="container">

      <Toaster position='top-center' reverseOrder={false}></Toaster>

      <div className='container_1'>

      <div className={`${styles.glass} ${extend.glass}`} style={{ width: "45%", paddingTop: '1em'}}>
      
          <div className="title">
            <h2 style={{textAlign:'center'}}>Profile</h2>
            <span className='text' style={{marginLeft:'190px'}}>
                You can update the details.
            </span>
          </div>

          <form className='forms' onSubmit={formik.handleSubmit}>
              <div className='profile' style={{marginLeft:'100px'}}>
                  <label htmlFor="profile">
                    <img src={file || avatar} className={`${styles.profile_img} ${extend.profile_img}`} alt="avatar" />
                  </label>
                  
                  <input onChange={onUpload} type="file" id='profile' name='profile' />
              </div>

              <div className="textbox" style={{marginLeft:'20px'}}>
                 <div className='name' style={{display:'flex'}}>
                     <input {...formik.getFieldProps('firstName')} className={`${styles.textbox} ${extend.textbox}`} type="text" placeholder='First Name' style={{padding:'15px',width:'180px'}} />
                     <input {...formik.getFieldProps('lastName')} className={`${styles.textbox} ${extend.textbox}`} type="text" placeholder='Last Name' style={{padding:'15px',width:'180px'}} />
                 </div>
                 <div className='name' style={{display:'flex'}} >
                     <input {...formik.getFieldProps('mobile')} className={`${styles.textbox} ${extend.textbox}`} type="text" placeholder='Mobile No' style={{padding:'15px',width:'180px'}} />
                     <input {...formik.getFieldProps('email')} className={`${styles.textbox} ${extend.textbox}`} type="text" placeholder='Email' style={{padding:'15px',width:'180px'}}/>
                 </div>
                 <input {...formik.getFieldProps('address')} className={`${styles.textbox} ${extend.textbox}`} type="text" placeholder='Address' style={{padding:'12px',marginLeft:'30px'}}/>
                  <button className={styles.btn} type='submit' style={{padding:'5px'}}>Update</button>
               
              </div>

              <div className="txt">
                <span className='txt1'style={{marginLeft:'150px',gap:'15px'}}>Change Password? <Link className='txt2' to="/reset">Reset Password</Link></span><br></br>
                <span className='txt1'style={{marginLeft:'200px',marginBottom:'15px'}}>Come back? <Link className='txt2' to="/">Log Out</Link></span>
              </div>

          </form>

        </div>
      </div>
    </div>
  )
}