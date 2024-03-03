import "../assets/styles/header.css";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import AuthService from "../api-service/authService";
import AuthVerify from "../api-service/authVerify";
import Logo from "./logo";
import useCategories from "../hooks/useCategories";
import AnchorLink from 'react-anchor-link-smooth-scroll';
import Cart from "./cart";

function Header() {
  const [userMode, setUserMode] = useState(false);
  const [isNavOpen, setIsNavOpen] = useState(false);
  const [categories] = useCategories();
  const [isCartOpen, setCart] = useState(false);
  const [noOfCartItems, setNoOfCartItems] = useState(0);

  const handleNav = () => {
    setIsNavOpen(!isNavOpen);
  };

  const toggleCart = () => {
    setCart(!isCartOpen)
  }

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setUserMode(true);
    } else {
      setUserMode(false);
    }
  }, []);

  const navigate = useNavigate();

  const logout = () => {
    AuthService.logout_req();
    navigate("/");
    window.location.reload();
  };

  return (
    <>
      <header className="app-header">
        <div className="logo-wrapper">
          <span>
            {isNavOpen ? (
              <i className="fa fa-times" aria-hidden="true" onClick={handleNav}></i>
            ) : (
              <i className="fa fa-bars" aria-hidden="true" onClick={handleNav}></i>
            )}
          </span>
          <span>
            <Link to="/">
              <Logo />
            </Link>
          </span>
        </div>
        <ul className={isNavOpen ? "nav-open" : "nav-close"}>
          <li>
            <AnchorLink href="#hero" className="nav-link">
              Home
            </AnchorLink>
          </li>
          <li>
            <AnchorLink href="#menu" className="nav-link">
              Menu
            </AnchorLink>
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
          </li>
          {!userMode && (
            <li>
              <Link to="/auth/login" className="nav-link">
                Login
              </Link>
            </li>
          )}
          {userMode && (
            <>
              <li>
                <Link to='/my/orders' className="nav-link">My account</Link>
              </li>
              <li onClick={logout} className="nav-link">
                <Link className="nav-link">Logout</Link>
              </li>
            </>
          )}
        </ul>
        <ul>
          <li>
            <Link to='/search'>
              <i className="fa fa-search" aria-hidden="true"></i>
            </Link>
          </li>
          <li>
            <Link onClick={toggleCart}>
              <i className="fa fa-shopping-cart" aria-hidden="true"></i>
              <span>({noOfCartItems})</span>
            </Link>
          </li>
        </ul>

        <AuthVerify logOut={logout} />
      </header>
      <Cart isCartOpen={isCartOpen} setIsCartOpen={setCart} onClose={() => setCart(false)} setNoOfCartItemsInHeader={setNoOfCartItems} />
    </>
  );

}
export default Header;
