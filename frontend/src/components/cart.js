import React, { useState } from "react";
import "../assets/styles/cart.css";
import { AiFillCloseCircle } from "react-icons/ai";
import { RiDeleteBin6Line } from "react-icons/ri";

// have to update something in header.js
const cart = ({ onClose }) => {
  const [products, setProducts] = useState();
  const fetchCartItems = async () => {
    try {
      const response = await fetch("/cart");
      const cartData = await response.json();
      setProducts(cartData.products);
    } catch (error) {
      console.error("Error fetching cart items:", error);
    }
  };

  const onQuantityChange = async (productId, newQuantity) => {
    try {
      const response = await fetch("/cart", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ productId, quantity: newQuantity }),
      });
      if (response.ok) {
        await fetchCartItems();
      } else {
        console.error("Error updating cart:", response.statusText);
      }
    } catch (error) {
      console.error("Error updating quantity:", error);
    }
  };

  const onProductRemove = async (product) => {
    try {
      const response = await fetch("/cart/" + product.id, { method: "DELETE" });
      if (response.ok) {
        await fetchCartItems();
      } else {
        console.error("Error removing product:", response.statusText);
      }
    } catch (error) {
      console.error("Error removing product:", error);
    }
  };

  return (
    <div className="shoppingCart">
      <div className="header">
        <h2>Shopping cart</h2>
        <button className="btn close-btn" onClick={onClose}>
          <AiFillCloseCircle size={30} />
        </button>
      </div>
      <div className="cart-products">
        {products.length === 0 && (
          <span className="empty-text">No items in your cart</span>
        )}
        {products.map((product) => (
          <div className="cart-product" key={product.id}>
            <img src={product.image} alt={product.name} />
            <div className="product-info">
              <h3>{product.name}</h3>
              <span className="product-price">
                {product.price * product.count}$
              </span>
            </div>
            <div className="quantity-control">
              <button
                className="btn quantity-btn"
                onClick={() => onQuantityChange(product.id, product.count - 1)}
                disabled={product.count <= 1}
              >
                -
              </button>
              <span className="count">{product.count}</span>
              <button
                className="btn quantity-btn"
                onClick={() => onQuantityChange(product.id, product.count + 1)}
              >
                +
              </button>
            </div>
            <button
              className="btn remove-btn"
              onClick={() => onProductRemove(product)}
            >
              <RiDeleteBin6Line size={20} />
            </button>
          </div>
        ))}
        {products.length > 0 && (
          <button className="btn checkout-btn">Proceed to checkout</button>
        )}
      </div>
    </div>
  );
};

export default cart;
