import React, { useState, useEffect } from "react";
import "../assets/styles/cart.css";
import { AiOutlineClose, AiOutlinePlus, AiOutlineMinus } from "react-icons/ai";
import { RiDeleteBin6Line } from "react-icons/ri";
import API_BASE_URL from "../api-service/apiConfig";
import axios from 'axios';
import AuthService from "../api-service/authService";

const Cart = ({ isCartOpen, onClose, setNoOfCartItemsInHeader }) => {
  const [products, setProducts] = useState([]);
  const [isLoading, setLoading] = useState(false);
  const [noOfCartItems, setNoOfCartItems] = useState(0);
  const [subtotal, setSubtotal] = useState(0.0);

  const fetchCartItems = async () => {
    setLoading(true)
    try {
      const response = await axios.get(API_BASE_URL + '/user/cart/getCartItems',
        {
          headers: AuthService.authHeader(),
          params: {
            email: AuthService.getCurrentUser().email
          }
        }).then(
          (response) => {
            console.log(response);
            if (response.data.status === "SUCCESS") {
              setProducts(response.data.response.cartItemResponse)
              setNoOfCartItems(response.data.response.noOfCartItems)
              setNoOfCartItemsInHeader(response.data.response.noOfCartItems)
              setSubtotal(response.data.response.subtotal)
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
    setLoading(false)
  };

  const onQuantityChange = async (productId, quantity) => {
    setLoading(true)
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
              window.location.reload()
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
    setLoading(false)
  };

  const onProductRemove = async (cartItemId) => {
    setLoading(true)
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
              window.location.reload()
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
    setLoading(false)
  };
  
  useEffect(() => {
    fetchCartItems()
  }, []);

  return (
    <div className={isCartOpen ? "shoppingCart active" : "shoppingCart"}>
      <div className="header">
        <h2>Your cart</h2>
        <div className="btn close-btn" onClick={onClose}>
          <AiOutlineClose size={20} />
        </div>
      </div>
      <div className="cart-products">
        {products.length === 0 && (
          <span className="empty-text">{isLoading ? "Loading..." : "Your cart is empty!"}</span>
        )}
        {products.map((cartItem) => (
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
        {products.length > 0 && (
          <>
            <h3>Subtotal: Rs. {subtotal}</h3>
            <button className="btn checkout-btn">Proceed to checkout</button>
          </>
        )}
      </div>
    </div>
  );
};

export default Cart;
