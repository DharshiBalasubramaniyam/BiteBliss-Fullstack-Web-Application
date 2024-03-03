import useCategories from "../hooks/useCategories";
import '../assets/styles/footer.css'
import CopyRight from "./copyright";

function Footer() {

    const [categories] = useCategories();

    return (
        <>
            <footer>
                <div>
                    <h1>About Us</h1>
                    <p>At BiteBliss, we believe that food is not just a necessity; it's an experience. 
                        Our journey began with a simple vision: to create a culinary haven where flavors, traditions, 
                        and innovation intertwine to delight the senses.
                    </p>
                    <button>Learn more</button>
                </div>

                <div>
                    <h1>Our Menu</h1>
                    <ul>
                        {
                            categories.map(cat => {
                                return <li key={cat.category_id}>
                                    <a href={"#" + cat.category_name} className="nav-link">
                                        {cat.category_name}
                                    </a>
                                </li>
                            })
                        }
                    </ul>
                </div>

                <div>
                    <h1>Contact Us</h1>
                    <div>
                        <p><span><i className="fa fa-map-marker"></i></span>No 123, Lotus strret, Colombo</p>
                        <p><span><i className="fa fa-phone"></i></span>+94 741258963, 9632587412</p>
                        <p><span><i className="fa fa-envelope"></i></span>bitebliss@abc.com</p>
                    </div>
                    <p>
                        <span><i className="fa fa-facebook"></i></span>
                        <span><i className="fa fa-twitter"></i></span>
                        <span><i className="fa fa-instagram"></i></span>
                        <span><i className="fa fa-youtube"></i></span>
                    </p>
                </div>
            </footer>
            <CopyRight />
        </>
    )
}

export default Footer;