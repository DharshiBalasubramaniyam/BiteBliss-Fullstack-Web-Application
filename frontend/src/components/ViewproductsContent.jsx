import React, { useRef, useState } from 'react';
import AliceCarousel from 'react-alice-carousel';
import Viewproducts from './Viewproducts';
import { FaChevronRight, FaChevronLeft } from 'react-icons/fa';


const ViewproductsContent = ({ productDetails }) => {
  const [activeIndexes, setActiveIndexes] = useState({});
  const carouselRefs = useRef({});

  const responsive = {
    0: { items: 1 },
    700: { items: 2 },
    950: { items: 3 },
    1150: { items: 4 },
  };

  const getCurrentBreakpoint = () => {
    const screenWidth = window.innerWidth;
    const breakpoints = Object.keys(responsive)
      .map(Number)
      .sort((a, b) => b - a);
    return breakpoints.find((breakpoint) => screenWidth >= breakpoint);
  };

  const currentBreakpoint = getCurrentBreakpoint();

  const slidePrev = (category) => {
    if (carouselRefs.current[category]) {
      carouselRefs.current[category].slidePrev();
    }
  };


  const slideNext = (category) => {
    if (carouselRefs.current[category]) {
      carouselRefs.current[category].slideNext();
    }
  };

  const syncActiveIndex = ({ item, category }) => {
    setActiveIndexes((prevIndexes) => ({ ...prevIndexes, [category]: item }));
  };

  const renderProductsByCategory = () => {

    if (!productDetails) {
      return <p>No products available.</p>;
    }

    const groupedProducts = productDetails.reduce((acc, product) => {
      const categoryId = product.category.category_id;

      if (!acc[categoryId]) {
        acc[categoryId] = { categoryName: product.category.category_name, products: [] };
      }

      acc[categoryId].products.push(product);

      return acc;
    }, {});

    return Object.values(groupedProducts).map((category) => (
      <div key={category.categoryName}>
        <div className='products-main'>
      <div style={{ position: "relative", padding: "30px 40px 30px"}}>
        <h2 className='title'>{category.categoryName}</h2>
        <AliceCarousel
          ref={(el) => (carouselRefs.current[category.categoryName] = el)}
          items={category.products.map((product) => (
            <Viewproducts products={product} key={product.product_id} />
          ))}
          // infinite
          responsive={responsive}
          disableDotsControls
          disableButtonsControls
          onSlideChanged={(item) => syncActiveIndex({ item, category: category.categoryName })}
          activeIndex={activeIndexes[category.categoryName] || 0}
        />

        {activeIndexes[category.categoryName] !== 0 && (
          <button onClick={() => slidePrev(category.categoryName)} className='arrow-prev'>
            <FaChevronLeft />
          </button>
        )}


        {activeIndexes[category.categoryName] !==
          category.products.length - responsive[currentBreakpoint]?.items && (
          <button onClick={() => slideNext(category.categoryName)} className='arrow-next'>
            <FaChevronRight />
          </button>
        )}
      </div>
      </div>
         </div>
    ));
  };

  return renderProductsByCategory();
};

export default ViewproductsContent;
