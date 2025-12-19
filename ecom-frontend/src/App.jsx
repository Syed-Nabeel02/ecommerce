/**
 * App.jsx
 * Main application component that sets up routing for the entire e-commerce app.
 * Handles public routes (home, products), protected routes (checkout, profile),
 * and admin-only routes (dashboard, product management).
 */

// React core library for building UI components
import React, { useState } from 'react'
import './App.css'
// React Router for navigation between pages
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
// Toast notifications library for user feedback messages
import { Toaster } from 'react-hot-toast'

// Public page components - accessible to all users
import HomeView from './views/HomePage/HomeView'
import ProductsView from './views/ProductsPage/ProductsView'
import CartView from './views/ShoppingCartPage/CartView'
import LoginView from './views/AuthenticationPage/LoginView'
import RegisterView from './views/AuthenticationPage/RegisterView'
import CheckoutView from './views/CheckoutPage/CheckoutView'
import ProfileView from './views/UserProfilePage/ProfileView'

// Admin dashboard components - only for admin users
import AdminLayoutWrapper from './views/AdminDashboardPage/AdminLayoutWrapper'
import DashboardView from './views/AdminDashboardPage/DashboardView'
import ProductManagementView from './views/AdminDashboardPage/ProductManagementView'
import OrderManagementView from './views/AdminDashboardPage/OrderManagementView'
import CategoryManagementView from './views/AdminDashboardPage/CategoryManagementView'
import CustomerManagementView from './views/AdminDashboardPage/CustomerManagementView'

// Reusable components used across the app
import TopNavBar from './components/ui/navigation/TopNavBar'
import PrivateRoute from './components/PrivateRoute'

// Order confirmation page
import OrderConfirmationView from './views/OrderConfirmationPage/OrderConfirmationView'

/**
 * Main App Component
 * Sets up the entire routing structure and navigation for the application
 */
function App() {
  return (
    <React.Fragment>
      <Router>
        <TopNavBar />
        <Routes>
          {/* Public routes - using new View components */}
          <Route path='/' element={ <HomeView />}/>
          <Route path='/products' element={ <ProductsView />}/>
          <Route path='/cart' element={ <CartView />}/>

          {/* Protected routes - authenticated users only */}
          <Route path='/' element={<PrivateRoute />}>
            <Route path='/checkout' element={ <CheckoutView />}/>
            <Route path='/order-confirm' element={ <OrderConfirmationView />}/>
            <Route path='/profile' element={ <ProfileView />}/>
          </Route>

          {/* Auth routes - using new View components */}
          <Route path='/' element={<PrivateRoute publicPage />}>
            <Route path='/login' element={ <LoginView />}/>
            <Route path='/register' element={ <RegisterView />}/>
          </Route>

          {/* Admin routes - admin users only */}
           <Route path='/' element={<PrivateRoute adminOnly />}>
            <Route path='/admin' element={ <AdminLayoutWrapper />}>
              <Route path='' element={<DashboardView />} />
              <Route path='products' element={<ProductManagementView />} />
              <Route path='customers' element={<CustomerManagementView />} />
              <Route path='orders' element={<OrderManagementView />} />
              <Route path='categories' element={<CategoryManagementView />} />
            </Route>
          </Route>
        </Routes>
      </Router>
      <Toaster position='bottom-center'/>
    </React.Fragment>
  )
}

export default App
