import axios from 'axios';
import API_BASE_URL from './apiConfig';

const get_categories = () => {
    return axios.get(
        API_BASE_URL + '/categories/viewCategory',
    )
}

const CategoryService = {
    get_categories
}

export default CategoryService;