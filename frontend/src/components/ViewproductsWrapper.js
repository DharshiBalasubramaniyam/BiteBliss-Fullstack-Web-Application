import React, { useState, useRef, useEffect } from 'react';
import ViewproductsContent from './ViewproductsContent';
import 'react-alice-carousel/lib/alice-carousel.css';
import { loadProduct } from "../api-service/Product_service";
import Loading from './loading';

function  ViewproductsWrapper(){
  const [productDetails, setProductDetails] = useState(null);

  useEffect(() => {
    getProduct();
  }, []);

  const getProduct = () => {
    loadProduct()
      .then((data) => {
        setProductDetails(data);
        
      })
      .catch((error) => {
        console.log(error);
      });
  };

  if (productDetails === null) {
    return <Loading/>;
  }
  
  return (
  
    <div id='menu'>
      <ViewproductsContent productDetails={productDetails} />
    </div>
        
  );
};

export default ViewproductsWrapper;
