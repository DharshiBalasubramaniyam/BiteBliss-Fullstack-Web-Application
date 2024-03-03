import API_BASE_URL from "./apiConfig";

export const searchProducts = async (searchKey) => {
  try {
    const response = await fetch(`${API_BASE_URL}/products/searchByCategoryAndName/${searchKey}`);
    if (!response.ok) {
      throw new Error(`HTTP error!! Status: ${response.status}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    return console.error('Error fetching data:', error);
  }
};
