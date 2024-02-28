import { Link } from "react-router-dom";
import "../assets/styles/search.css"
import { useEffect, useRef } from "react";
import Logo from "./logo";

function Search() {

    const searchInput = useRef(null)

    useEffect(()=> {
        searchInput.current.focus()
    }, [])
    
    return (
        <>
            <header className="app-header"><Logo/></header>
            <div className="search-section">
                <div className="search-input">
                    <Link to='/' className="nav-link">
                        <i class="fa fa-arrow-left"></i>
                    </Link>
                    <input
                        type="search"
                        placeholder="search our menu"
                        ref={searchInput}
                    />
                    <span>
                        <i class="fa fa-search"></i>
                    </span>
                </div>
                {/* <h2>Search results for <q>hello</q></h2>
                <p>10 results found!</p>
                <div className="products-wrapper">
                    display products here!
                </div> */}
            </div>
        </>
    )
}

export default Search;