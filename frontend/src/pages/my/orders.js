import { Link } from 'react-router-dom';
import '../../assets/styles/user.css';
import Header from '../../components/header';
import asian1 from '../../assets/images/asian1.jpg'
import Footer from '../../components/footer';
import Logo from '../../components/logo';
import UserHeader from '../../components/userHeader';

function Orders() {
    return (
        <>
            <UserHeader/>
            <div className='user'>
                <h1>Hello John!</h1>
                <h2>My orders</h2>
                <div className='orders'>
                    <div className='order'>
                        <div>
                            <div>Order #123456789</div>
                            <div>Placed on 25 oct 2023 18.30</div>
                        </div>
                        <div className='items'>
                            <div>
                                <img src={asian1}/>
                                <div>
                                    <div>Item 1</div>
                                    <div>Rs. 250 x 4</div>
                                </div>
                            </div>
                            <div>
                                <img src={asian1}/>
                                <div>
                                    <div>Item 1</div>
                                    <div>Rs. 250 x 4</div>
                                </div>
                            </div>
                            <div>
                                <img src={asian1}/>
                                <div>
                                    <div>Item 1</div>
                                    <div>Rs. 250 x 4</div>
                                </div>
                            </div>
                            <div>
                                <img src={asian1}/>
                                <div>
                                    <div>Item 1</div>
                                    <div>Rs. 250 x 4</div>
                                </div>
                            </div>
                            <div>
                                <img src={asian1}/>
                                <div>
                                    <div>Item 1</div>
                                    <div>Rs. 250 x 4</div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div>Total: Rs. 5000</div>
                            <div>Order Status: Delivered</div>
                            <div>Paid Status: Paid</div>
                            <div>Shipped address: 123, hello, hello</div>
                        </div>

                    </div>
                    

                </div>
            </div>
            <Footer/>

        </>
        
    )

}

export default Orders;