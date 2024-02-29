import React from "react";
import { NavLink } from "react-router-dom";
import { MdDashboard } from "react-icons/md";
import { CgProfile } from "react-icons/cg";
import { IoFastFoodOutline } from "react-icons/io5";
const UserMenu = () => {
  return (
    <div>
      <div className="text-center">
        <div className="list-group" style={{marginTop:'150px'}}>
          <h4 style={{color:'yellow'}}><MdDashboard />User Dashboard</h4>
          <NavLink
            to="/dashboard/user/profile"
            className="list-group-item list-group-item-action"
            style={{fontWeight:'bold'}}
          > <div style={{fontSize:'30px'}}><CgProfile /></div>
            My Profile
          </NavLink>
          <NavLink
            to="/dashboard/user/orders"
            className="list-group-item list-group-item-action"
            style={{fontWeight:'bold'}}
          ><div style={{fontSize:'30px'}}><IoFastFoodOutline /></div>
            My Orders
          </NavLink>
        </div>
      </div>
    </div>
  );
};

export default UserMenu;