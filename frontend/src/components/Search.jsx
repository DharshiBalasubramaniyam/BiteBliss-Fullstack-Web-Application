import React, { useState } from 'react';
import Viewproducts from './Viewproducts';
import { searchProducts } from "../api-service/Search_products";

const Search = () => {
  const [searchKey, setSearchKey] = useState('');
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');

  const onSearchResults = (key, results) => {
    console.log('Received data:', results);
    console.log(`Search results for '${key}':`, results.length);
   
    setResults(results);
  };

  const handleSearch = async () => {
    try {
      const results = await searchProducts(searchKey);
      onSearchResults(searchKey, results);
    } catch (error) {
      setError('No products found');
      setResults([]);
    }
  };

  return (
    <div>
      <input
        type="text"
        value={searchKey}
        onChange={(e) => setSearchKey(e.target.value)}
      />
      <button onClick={handleSearch}>Search</button>

      {error && <p>{error}</p>}
      <div className="search-results">
      {results.length > 0 && (
        <ul>
          {results.map((product) => (
            <Viewproducts key={product.product_id} products={product} />
          ))}
        </ul>
      )}
      </div>
    </div>
  );
};

export default Search;
