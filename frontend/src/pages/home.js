import Footer from "../components/footer";
import ViewproductsWrapper from "../components/ViewproductsWrapper";
import Header from "../components/header";
import Hero from "../components/hero";
import CopyRight from "../components/copyright";

function Home() {
    return (
        <>
            <Header/>
            <Hero/>
            <ViewproductsWrapper/>
            <Footer/>
            <CopyRight/>
        </>        
    )
}

export default Home;