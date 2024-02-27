import React from 'react'
import '../assets/styles/Viewproducts.css'


const Viewproducts = ({products}) => {
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
        <button type='submit'>Add to cart</button>

        </div>
      </div>
    </div>
  )
}

export default Viewproducts
