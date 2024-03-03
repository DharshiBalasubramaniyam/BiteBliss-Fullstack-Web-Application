import ViewproductsWrapper from "../components/ViewproductsWrapper";
import Footer from "../components/footer";
import Header from "../components/header";
import Hero from "../components/hero";

function Home() {
    return (
        <>
            <Header/>
            <Hero/>
            <ViewproductsWrapper/>
            <Footer/>
        </>        
    )
}

export default Home;