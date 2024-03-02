import { useEffect, useState } from "react";
import API_BASE_URL from "../api-service/apiConfig";
import axios from 'axios';
import AuthService from "../api-service/authService";

function useCartItems() {
    const [cartId, setCartId] = useState(null);
    const [cartItems, setCartItems] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [noOfCartItems, setNoOfCartItems] = useState(0);
    const [subtotal, setSubtotal] = useState(0.0);

    const fetchData = () => {
        fetchCartItems();
    }

    useEffect(() => {
        fetchCartItems()
    }, [])

    const fetchCartItems = async () => {
        setIsLoading(true)
        try {
            const response = await axios.get(API_BASE_URL + '/user/cart/getCartItems',
                {
                    headers: AuthService.authHeader(),
                    params: {
                        email: AuthService.getCurrentUser().email
                    }
                }).then(
                    (response) => {
                        if (response.data.status === "SUCCESS") {
                            console.log(response.data);
                            if (response.data.response.cartItemResponse) {
                                setCartId(response.data.response.cartId)
                                setCartItems(response.data.response.cartItemResponse)
                                setNoOfCartItems(response.data.response.noOfCartItems)
                                setSubtotal(response.data.response.subtotal)
                            }
                            return
                        }
                    },
                    (error) => {
                        console.log(error);
                    }
                )
        } catch (error) {
            console.error("Error fetching cart items:", error);
        }
        setIsLoading(false)
    };


    return [cartId, cartItems, noOfCartItems, subtotal, isLoading, setIsLoading, fetchData]
}

export default useCartItems;