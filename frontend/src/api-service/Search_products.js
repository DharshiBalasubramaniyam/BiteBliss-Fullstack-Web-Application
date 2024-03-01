import API_BASE_URL from "./apiConfig";

export const searchProducts = (searchKey) => {
  return fetch(`${API_BASE_URL}/products/searchByCategoryAndName/${searchKey}`)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error!! Status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => data)
    .catch(error => console.error('Error fetching data:', error));
};
