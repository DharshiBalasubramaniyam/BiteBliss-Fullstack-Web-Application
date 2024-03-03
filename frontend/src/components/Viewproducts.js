import React, { useState } from 'react'
import '../assets/styles/Viewproducts.css'
import API_BASE_URL from "../api-service/apiConfig";
import axios from 'axios';
import AuthService from "../api-service/authService";
import { useNavigate } from "react-router-dom";
import useCartItems from '../hooks/useCartItems';

function Viewproducts ({products}) {

  const [isLoading, setLoading] = useState(false)
  const navigate = useNavigate()

  const addToCart = async (productId) => {
    if (!AuthService.getCurrentUser()){
        navigate('/auth/login')
    }
    try {
      setLoading(true)
      const response = await axios.post(API_BASE_URL + '/user/cart/new',
        {
          productId: productId,
          quantity: 1
        },
        {
          headers: AuthService.authHeader(),
          params: {
            email: AuthService.getCurrentUser().email
          }
        }).then(
          (response) => {
            console.log(response);
            if (response.data.status === "SUCCESS") {
              window.location.reload()
              return
            }
          },
          (error) => {
            console.log(error);
          }
        )
    } catch (error) {
      console.error("Error adding cart items:", error);
    }
    setLoading(false)
  };


  return (
    <div className='products'>
      <div className='box-container'>
        <div className='box'>
          <img src={require(`../assets/images/${products.imageUrl}`)} className="image" ></img>
          <div className='price' aria-label='image'>{products.price}</div>
          <div className='text-part'>
            <div className='name'>{products.product_name}</div>
            <div className='description'>{products.description}</div>
          </div>
          <button 
            className={isLoading ? "loading" : ""}
            type='submit' 
            onClick={() => addToCart(products.product_id)}
          >
            {
              isLoading ? "Adding to cart..." : "Add to cart"
            }
          </button>
        </div>
      </div>
    </div>
  )
}

export default Viewproducts;
