import React from "react";
import "../assets/styles/cart.css";
import { AiFillCloseCircle } from "react-icons/ai";
import { RiDeleteBin6Line } from "react-icons/ri";

const cart = () => {
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
