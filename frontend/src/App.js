import { Routes, Route, Navigate, Outlet } from "react-router-dom";
import Login from './pages/auth/login/login';
import Home from './pages/home';
import Register from './pages/auth/register/register';
import RegistrationSuccessful from './pages/auth/register/registrationSuccessful';
import ForgotPasswordEmailVerification from './pages/auth/forgotPassword/emailVerification';
import ResetPassword from './pages/auth/forgotPassword/resetPassword';
import ResetPasswordSuccessful from './pages/auth/forgotPassword/resetPasswordSuccessful';
import RegistrationVerfication from './pages/auth/register/registrationVerfication';
import ForgotPasswordCodeVerification from './pages/auth/forgotPassword/forgotPasswordCodeVerificaion';
import NotFound404 from './pages/auth/notFound404';
import Search from './components/search';
import Orders from './pages/my/orders';
import Profile from './pages/my/profile';
import ChangePassword from './pages/my/changePassword';
import CheckoutForm from './components/checkOut';
import UnAuthorize401 from './pages/auth/unAuthorize401';
import AuthService from './api-service/authService';
import OrderSuccessful from "./pages/my/orderSuccess";

function App() {

  const ProtectedRoute = ({ isAllowed, redirectPath = '/unauthorized', children }) => {
    if (!isAllowed) {
      return <Navigate to={redirectPath} replace />;
    }

    return children ? children : <Outlet />
  }

  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/auth/login" element={<Login />} />
      <Route path='/search' element={<Search />} />
      <Route path="/unauthorized" element={<UnAuthorize401 />} />
      <Route path="*" element={<NotFound404 />} />

      <Route element={<ProtectedRoute isAllowed={!AuthService.getCurrentUser()} />}>
        <Route path="/auth/register" element={<Register />} />
        <Route path="/auth/userRegistrationVerfication/:email" element={<RegistrationVerfication />} />
        <Route path="/auth/success-registration" element={<RegistrationSuccessful />} />
        <Route path="/auth/forgotPassword/verifyEmail" element={<ForgotPasswordEmailVerification />} />
        <Route path="/auth/forgotPassword/verifyAccount/:email" element={<ForgotPasswordCodeVerification />} />
        <Route path="/auth/forgotPassword/resetPassword/:email" element={<ResetPassword />} />
        <Route path="/auth/forgotPassword/success" element={<ResetPasswordSuccessful />} />
      </Route>

      <Route element={<ProtectedRoute isAllowed={AuthService.getCurrentUser() && AuthService.getCurrentUser().roles.includes("ROLE_USER")} />}>
        <Route path='/my/orders' element={<Orders />} />
        <Route path='/my/profile' element={<Profile />} />
        <Route path='/my/profile/changepassword' element={<ChangePassword />} />
        <Route path='/my/order/checkout' element={<CheckoutForm />} />
        <Route path='/my/order/success' element={<OrderSuccessful />} />
      </Route>

    </Routes>
  );
}

export default App;
