import React, { useState, useRef, useEffect } from 'react';
import ViewproductsContent from './ViewproductsContent';
import 'react-alice-carousel/lib/alice-carousel.css';
import { loadProduct } from "../api-service/Product_service";

const ViewproductsWrapper = () => {
  const [productDetails, setProductDetails] = useState(null);

  useEffect(() => {
    getProduct();
  }, []);

  const getProduct = () => {
    loadProduct()
      .then((data) => {
        console.log("Received data:", data);
        setProductDetails(data);
        
      })
      .catch((error) => {
        console.log(error);
      });
  };

  if (productDetails === null) {
    return <p>Loading products...</p>;
  }
  
  return (
  
          <ViewproductsContent productDetails={productDetails} />
        
  );
};

export default ViewproductsWrapper;
