import React from "react";
import Layout from "./../components/Layout";
import { BiFontSize, BiMailSend, BiPhoneCall} from "react-icons/bi";
import contact from "../assets/images/contactus.jpeg"
const Contact = () => {
  return (
    <Layout>
      <div className="row contactus ">
        <div className="col-md-6 ">
          <img
            src={contact}
            alt="contactus"
            style={{ width: "100%" }}
          />
        </div>
        <div className="col-md-4">
          <h1 className="bg-dark p-2 text-white text-center">CONTACT US</h1>
          <p className="text-justify mt-2"  style={{fontSize:'25px'}}>
            any query and info about our foods feel free to call anytime we 24X7
            vaialible
          </p>
          <p className="mt-3" style={{fontSize:'20px'}}>
            <BiMailSend /> : www.Bitebliss@gmail.com
          </p>
          <p className="mt-3" style={{fontSize:'20px'}}>
            <BiPhoneCall /> : 0773390121
          </p>
          
        </div>
      </div>
    </Layout>
  );
};

export default Contact;