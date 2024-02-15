import React from "react";
import Layout from "../components/Layout";
import about from "../assets/images/about.jpeg"
const About = () => {
  return (
    <Layout>
      <div className="row contactus ">
        <div className="col-md-6 ">
          <img
            src={about}
            alt="contact us"
            style={{ width: "100%",margin:'30px' }}
          />
        </div>
        <div className="col-md-4">
          <p style={{marginTop:'7rem',textAlign:"justify",marginLeft:'4rem'}}>
          At BiteBliss, we believe that food is not just a necessity; it's an experience. 
          Our journey began with a simple vision: to create a culinary haven where flavors, traditions, 
          and innovation intertwine to delight the senses.
          </p>
        </div>
      </div>
    </Layout>
  );
};

export default About;