import { Link } from 'react-router-dom';
import '../../assets/styles/user.css';
import Header from '../../components/header';
import asian1 from '../../assets/images/asian1.jpg'
import Footer from '../../components/footer';
import Logo from '../../components/logo';
import UserHeader from '../../components/userHeader';
import UserService from '../../api-service/userService';
import { useEffect, useState } from 'react';
import AuthService from '../../api-service/authService';
import Loading from '../../components/loading';
import axios from 'axios';
import API_BASE_URL from "../../api-service/apiConfig";

function Orders() {

    const [isLoading, setLoading] = useState(false);
    const [orders, setOrders] = useState(null);
    console.log(orders)

    const getOrders = async () => {
        setLoading(true)
        try {
            const response = await axios.get(
                API_BASE_URL + '/user/orders',
                {
                    headers: AuthService.authHeader(),
                    params: {
                        email: AuthService.getCurrentUser().email
                    }
                }).then(
                    (response) => {
                        console.log(response);
                        if (response.data.status === "SUCCESS") {
                            setOrders(response.data.response)
                            console.log(orders)
                        }
                    },
                    (error) => {
                        console.log(error);
                    }
                )
        } catch (error) {
            console.error("Error fetching cart items:", error);
        }
        setLoading(false)
    };

    useEffect(() => {
        getOrders()
    }, [])


    return (
        <>
            <UserHeader />
            <div className='user'>
                {
                    (isLoading) ? (
                        <Loading />
                    )
                        :
                        (!orders) ? (
                            <h2>Something went wrong. Please try again later!</h2>
                        )
                            :
                            (orders.length === 0) ? (
                                <h2>You have not placed any orders yet!</h2>
                            )
                                :
                                <>
                                    <h1>Hello {AuthService.getCurrentUser().username}!</h1>
                                    <h2>My orders</h2>
                                    <div className='orders'>
                                        {
                                            orders.map((order) => {
                                                return (
                                                    <div className='order' key={order.orderId}>
                                                        <div>
                                                            <div>Order #{order.orderId}</div>
                                                            <div>Placed on {order.placedOn.split("T")[0]} {order.placedOn.split("T")[1]}</div>
                                                        </div>
                                                        <div className='items'>
                                                            {
                                                                order.orderItems.map((item) => {
                                                                    return (
                                                                        <div key={item.orderItemId}>
                                                                            <img src={require(`../../assets/images/${item.imageUrl}`)} />
                                                                            <div>
                                                                                <div>{item.productName}</div>
                                                                                <div>Rs. {item.productPrice} x {item.productQuantity}</div>
                                                                            </div>
                                                                        </div>
                                                                    )
                                                                })
                                                            }
                                                        </div>
                                                        <div>
                                                            <div>Total: Rs. {order.orderAmt}</div>
                                                            <div>Order Status: {order.orderStatus}</div>
                                                            <div>Paid Status: {order.paymentStatus}</div>
                                                            <div>Shipped address: {order.addressLine1} {order.addressLine2}</div>
                                                        </div>

                                                    </div>
                                                )
                                            })
                                        }


                                    </div>
                                </>}

            </div>
            <Footer />

        </>

    )

}

export default Orders;