import React, { useState, useEffect } from "react";
import "../assets/styles/cart.css";
import { AiOutlineClose, AiOutlinePlus, AiOutlineMinus } from "react-icons/ai";
import { RiDeleteBin6Line } from "react-icons/ri";
import API_BASE_URL from "../api-service/apiConfig";
import axios from 'axios';
import AuthService from "../api-service/authService";
import { Link, useNavigate } from "react-router-dom";
import useCartItems from "../hooks/useCartItems";

const Cart = ({ isCartOpen, onClose, setNoOfCartItemsInHeader }) => {

  const [cartId, cartItems, noOfCartItems, subtotal, isLoading, setIsLoading, fetchData] = useCartItems()
  const navigate = useNavigate();

  useEffect(() => {
    setNoOfCartItemsInHeader(noOfCartItems)
  }, [noOfCartItems])

  const onQuantityChange = async (productId, quantity) => {
    setIsLoading(true)
    try {
      const response = await axios.put(API_BASE_URL + '/user/cart/update',
        {
          productId: productId,
          quantity: quantity
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
              fetchData()
              return
            }
          },
          (error) => {
            console.log(error);
          }
        )
    } catch (error) {
      console.error("Error fetching cart items:", error);
    }
    setIsLoading(false)
  };

  const onProductRemove = async (cartItemId) => {
    setIsLoading(true)
    try {
      const response = await axios.delete(API_BASE_URL + '/user/cart/delete',
        {
          headers: AuthService.authHeader(),
          params: {
            cartItemId: cartItemId
          }
        }).then(
          (response) => {
            console.log(response);
            if (response.data.status === "SUCCESS") {
              fetchData()
              return
            }
          },
          (error) => {
            console.log(error);
          }
        )
    } catch (error) {
      console.error("Error fetching cart items:", error);
    }
    setIsLoading(false)
  };

  const onCheckout = () => {
    const cart = {cartId: cartId, subtotal: subtotal}
    localStorage.setItem("cart", JSON.stringify(cart))
    navigate('/my/order/checkout');
  }

  return (
    <div className={isCartOpen ? "shoppingCart active" : "shoppingCart"}>
      <div className="header">
        <h2>Your cart</h2>
        <div className="btn close-btn" onClick={onClose}>
          <AiOutlineClose size={20} />
        </div>
      </div>
      <div className="cart-products">
        {cartItems.length === 0 && (
          <span className="empty-text">{isLoading ? "Loading..." : "Your cart is empty!"}</span>
        )}
        {cartItems.map((cartItem) => (
          <div className="cart-product" key={cartItem.cartItemId}>
            <img src={require(`../assets/images/${cartItem.imageUrl}`)} alt={cartItem.productName} />
            <div className="product-info">
              <h4>
                {cartItem.productName}
                <div
                  className={cartItem.quantity === 20 || isLoading ? "btn close-btn disable" : "btn close-btn"}
                  onClick={() => onProductRemove(cartItem.cartItemId)}
                >
                  <RiDeleteBin6Line size={20} className="" />
                </div>

              </h4>
              <span className="product-price">
                {cartItem.price} x {cartItem.quantity} = Rs. {cartItem.totalPrice}
              </span>
              <div className="quantity-control">
                <span
                  className={cartItem.quantity === 1 || isLoading ? "disable" : ""}
                  onClick={() => onQuantityChange(cartItem.product_id, -1)}
                >
                  <AiOutlineMinus size={18} />
                </span>
                <span className="count">{cartItem.quantity}</span>
                <span
                  className={cartItem.quantity === 20 || isLoading ? "disable" : ""}
                  onClick={() => onQuantityChange(onQuantityChange(cartItem.product_id, 1))}
                >
                  <AiOutlinePlus size={18} />
                </span>
              </div>

            </div>

          </div>
        ))}
      </div>
      <div className="cart-summary">
        {cartItems.length > 0 && (
          <>
            <h3>Subtotal: Rs. {subtotal}</h3>
            <button className="btn checkout-btn" onClick={onCheckout}>Proceed to checkout</button>
          </>
        )}
      </div>
    </div>
  );
};

export default Cart;
