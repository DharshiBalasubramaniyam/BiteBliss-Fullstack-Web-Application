import API_BASE_URL from "./apiConfig";

export const loadProduct = () => {
    return fetch(`${API_BASE_URL}/products/view`)
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => data)
      .catch(error => console.error('Error fetching data:', error));
  };