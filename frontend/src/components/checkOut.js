import React, { useEffect, useState } from 'react';
import API_BASE_URL from "../api-service/apiConfig";
import axios from 'axios';
import AuthService from "../api-service/authService";
import { Link, useNavigate } from "react-router-dom";
import '../assets/styles/checkout.css'
import Logo from './logo';
import CopyRight from './copyright';

const CheckoutForm = () => {
  const [fname, setFname] = useState('');
  const [lname, setLname] = useState('');
  const [address1, setAddress1] = useState('');
  const [address2, setAddress2] = useState('');
  const [city, setCity] = useState('');
  const [district, setDistrict] = useState('');
  const [phone, setPhone] = useState('');
  const [cartInfo, setCartInfo] = useState(null);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false)
  const navigate = useNavigate();

  const [fnameError, setFnameError] = useState('');
  const [lnameError, setLnameError] = useState('');
  const [address1Error, setAddress1Error] = useState('');
  const [cityError, setCityError] = useState('');
  const [districtError, setDistrictError] = useState('');
  const [phoneError, setPhoneError] = useState('');

  useEffect(() => {
    const cart = JSON.parse(localStorage.getItem("cart"));
    if (cart) {
      setCartInfo(cart);

    }

    const handleUnLoad = (event) => {
      event.preventDefault();
      localStorage.removeItem("cart")
    }

    window.addEventListener("beforeunload", handleUnLoad)

    return () => {
      window.removeEventListener("beforeunload", handleUnLoad);
    }
  }, [])

  const handleSubmit = async (e) => {
    if (!cartInfo) {
      navigate('/'); return;
    }
    e.preventDefault();
    // Reset previous errors
    setFnameError('');
    setLnameError('');
    setAddress1Error('');
    setCityError('');
    setDistrictError('');
    setPhoneError('');

    // Validate First Name
    if (!fname.trim()) {
      setFnameError('First Name is required');
      return;
    }

    // Validate Last Name
    if (!lname.trim()) {
      setLnameError('Last Name is required');
      return;
    }

    // Validate Address Line 1
    if (!address1.trim()) {
      setAddress1Error('Address Line 1 is required');
      return;
    }

    // Validate City
    if (!city.trim()) {
      setCityError('City is required');
      return;
    }

    // Validate District
    if (!district) {
      setDistrictError('Please select a District');
      return;
    }

    // Validate Phone Number
    const phoneRegex = /^[0-9]{10}$/;
    if (!phone.match(phoneRegex)) {
      setPhoneError('Please enter a valid 10-digit phone number');
      return;
    }

    // Form is valid, proceed with submission logic
    console.log('Form submitted successfully:', { fname, lname, address1, address2, city, district, phone });
    setIsLoading(true)
    try {
      //need to update url
      const response = await axios.post(API_BASE_URL + "/user/order/create",
        {
          firstName: fname,
          lastName: lname,
          addressLine1: address1,
          addressLine2: address2,
          city: city,
          district: district,
          phone: phone,
          cartId: cartInfo.cartId,
          userId: AuthService.getCurrentUser().id,
          total: cartInfo.subtotal
        },
        {
          headers: AuthService.authHeader()
        }
      );

      console.log(response)

      if (response.data.status === "SUCCESS") {
        localStorage.removeItem("cart")
        navigate('/my/order/success')
        setFname('');
        setLname('');
        setAddress1('');
        setAddress2('');
        setCity('');
        setDistrict('');
        setPhone('');
      } else {
        setError("Failed to place order: Please try again later.")
      }
    } catch (error) {
      setError("Failed to place order: Please try again later.")
    }
    setIsLoading(false)

  };

  return (
    <>
      <header className='app-header'><Logo /></header>
      <div className="checkout-container">
        <h1>Checkout</h1>
        <form onSubmit={handleSubmit}>
          <small className="text-danger">{error}</small>
          <div className="input-box">
            <label htmlFor="fname" className="form-label">
              First Name
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Eg. John"
              name="fname"
              value={fname}
              onChange={(e) => setFname(e.target.value)}
            />
            <small className="text-danger">{fnameError}</small>
          </div>
          <div className="input-box">
            <label htmlFor="lname" className="form-label">
              Last Name
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Eg. Smith"
              name="lname"
              value={lname}
              onChange={(e) => setLname(e.target.value)}
            />
            <small className="text-danger">{lnameError}</small>
          </div>
          <div className="input-box">
            <label htmlFor="address1" className="form-label">
              Address Line 1
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Eg. House No, Street No, Area"
              name="address1"
              value={address1}
              onChange={(e) => setAddress1(e.target.value)}
            />
            <small className="text-danger">{address1Error}</small>
          </div>
          <div className="input-box">
            <label htmlFor="address2" className="form-label">
              Address Line 2
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Eg. Suite no / floor No"
              name="address2"
              value={address2}
              onChange={(e) => setAddress2(e.target.value)}
            />
          </div>
          <div className="input-box">
            <label htmlFor="city" className="form-label">
              City
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Eg. Point Pedro"
              name="city"
              value={city}
              onChange={(e) => setCity(e.target.value)}
            />
            <small className="text-danger">{cityError}</small>
          </div>
          <div className="input-box">
            <label htmlFor="district" className="form-label">
              District
            </label>
            <select
              className="form-select"
              name="district"
              value={district}
              onChange={(e) => setDistrict(e.target.value)}
            >
              <option value="">Select District</option>
              <option value="Colombo">Colombo</option>
              <option value="Gampaha">Gampaha</option>
              <option value="Kalutara">Kalutara</option>
              <option value="Kandy">Kandy</option>
              <option value="Matale">Matale</option>
              <option value="Nuwara Eliya">Nuwara Eliya</option>
              <option value="Galle">Galle</option>
              <option value="Matara">Matara</option>
              <option value="Hambantota">Hambantota</option>
              <option value="Jaffna">Jaffna</option>
              <option value="Kilinochchi">Kilinochchi</option>
              <option value="Mannar">Mannar</option>
              <option value="Mullaitivu">Mullaitivu</option>
              <option value="Vavuniya">Vavuniya</option>
              <option value="Ratnapura">Ratnapura</option>
              <option value="Kegalle">Kegalle</option>
              <option value="Trincomalee">Trincomalee</option>
              <option value="Batticaloa">Batticaloa</option>
              <option value="Ampara">Ampara</option>
              <option value="Badulla">Badulla</option>
              <option value="Monaragala">Monaragala</option>
              <option value="Polonnaruwa">Polonnaruwa</option>
              <option value="Anuradhapura">Anuradhapura</option>
              <option value="Puttalam">Puttalam</option>
              <option value="Kurunegala">Kurunegala</option>
            </select>
            <small className="text-danger">{districtError}</small>
          </div>
          <div className="input-box">
            <label htmlFor="phone" className="form-label">
              Phone Number
            </label>
            <input
              type="text"
              className="form-control"
              placeholder="Eg. 07xxxxxxxx"
              name="phone"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />
            <small className="text-danger">{phoneError}</small>
          </div>
          <button 
            type="submit"
            className={isLoading ? "loading" : ""}
            name="proceed">
            {isLoading ? "Processing..." : "Place Order"}
          </button>
        </form>
      </div>
      <CopyRight />
    </>
  );
};

export default CheckoutForm;
