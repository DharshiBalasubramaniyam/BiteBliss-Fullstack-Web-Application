import { Link, useNavigate } from "react-router-dom";
import "../assets/styles/search.css"
import { useEffect, useRef, useState } from "react";
import Logo from "./logo";
import Viewproducts from './Viewproducts';
import { searchProducts } from "../api-service/Search_products";
import Footer from "./footer";
import useCartItems from "../hooks/useCartItems";
import AuthService from "../api-service/authService";
import API_BASE_URL from "../api-service/apiConfig";
import axios from 'axios';

function Search() {

    const searchInput = useRef(null)
    const [searchKey, setSearchKey] = useState('');
    const [results, setResults] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [isAdding, setIsAdding] = useState(false)
    const navigate = useNavigate()

    const addToCart = async (productId) => {
        if (!AuthService.getCurrentUser()) {
            navigate('/auth/login')
        }
        try {
            setIsAdding(true)
            const response = await axios.post(API_BASE_URL + '/user/cart/new',
                {
                    productId: productId,
                    quantity: 1
                },
                {
                    headers: AuthService.authHeader(),
                    params: {
                        email: AuthService.getCurrentUser().email
                    }
                }).then(
                    (response) => {
                        console.log(response);
                        if (response.data.status === "SUCCESS") {
                            return
                        }
                    },
                    (error) => {
                        console.log(error);
                    }
                )
        } catch (error) {
            console.error("Error adding cart items:", error);
        }
        setIsAdding(false)
    };

    const onSearchChange = (val) => {
        setResults(null)
        setSearchKey(val)
    }

    const handleSearch = async () => {
        if (searchKey == "") {
            return
        }
        setIsLoading(true)
        try {
            const results = await searchProducts(searchKey);
            console.log(results)
            setResults(results);
        } catch (error) {
            setResults(null);
        }
        setIsLoading(false)
    };

    useEffect(() => {
        searchInput.current.focus()

        const handleUnLoad = (event) => {
            event.preventDefault();
            window.location.reload();
        }

        window.addEventListener("beforeunload", handleUnLoad)

        return () => {
            window.removeEventListener("beforeunload", handleUnLoad);
        }
    }, [])

    return (
        <>
            <header className="app-header"><Logo /></header>
            <div className="search-section">
                <div className="search-input">
                    <Link to='/' className="nav-link">
                        <i class="fa fa-arrow-left"></i>
                    </Link>
                    <input
                        type="search"
                        placeholder="search our menu"
                        ref={searchInput}
                        value={searchKey}
                        onChange={(e) => onSearchChange(e.target.value)}
                    />
                    <span onClick={handleSearch}>
                        Go
                    </span>
                </div>
                {
                    !results & !isLoading ? <></> :
                        !results & isLoading ? <p>Loading...</p> :
                            results?.length == 0 ? <p>No products found!</p> :
                                <>
                                    <h3>Search results for <q>{searchKey}</q></h3>
                                    <p>{results.length} results found!</p>
                                    <div className="results">
                                        <ul>
                                            {results.map((product) => (
                                                <div className='box' key={product.product_id}>
                                                    <img src={require(`../assets/images/${product.imageUrl}`)} className="image" ></img>
                                                    <div className='price' aria-label='image'>{product.price}</div>
                                                    <div className='text-part'>
                                                        <div className='name'>{product.product_name}</div>
                                                        <div className='description'>{product.description}</div>
                                                    </div>
                                                    <button
                                                        className={isAdding ? "loading" : ""}
                                                        type='submit'
                                                        onClick={() => addToCart(product.product_id)}
                                                    >
                                                        {
                                                            isAdding ? "Adding to cart..." : "Add to cart"
                                                        }
                                                    </button>
                                                </div>
                                            ))}
                                        </ul>
                                    </div>
                                </>
                }

            </div>
            <Footer />
        </>
    )
}

export default Search;