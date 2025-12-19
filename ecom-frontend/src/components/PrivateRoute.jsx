/**
 * PrivateRoute.jsx
 * Protected route wrapper that controls access to pages based on authentication and user roles.
 * - Public pages: redirects logged-in users away (login/register)
 * - Admin pages: only accessible to admin users
 * - Private pages: only accessible to logged-in users
 */

// React core library
import React from 'react'
// Redux hook to access global state (check if user is logged in)
import { useSelector } from 'react-redux'
// React Router components for navigation and nested routes
import { Navigate, Outlet, useLocation } from 'react-router-dom';

/**
 * PrivateRoute Component
 * @param {boolean} publicPage - If true, only non-logged-in users can access (login/register pages)
 * @param {boolean} adminOnly - If true, only admin users can access (admin dashboard)
 */
const PrivateRoute = ({ publicPage = false, adminOnly = false }) => {
    // Get current logged-in user from Redux store
    const { currentUser } = useSelector((state) => state.authentication);
    // Check if user has admin role
    const isAdmin = currentUser && currentUser?.roles?.includes("ROLE_ADMIN");

    // For public pages (login/register), redirect logged-in users to home
    if (publicPage) {
        return currentUser ? <Navigate to="/" /> : <Outlet />
    }

    // For admin-only pages, redirect non-admin users to home
    if (adminOnly) {
        if (!isAdmin) {
            return <Navigate to="/"/>
        }
    }

    // For protected pages, redirect non-logged-in users to login
    return currentUser ? <Outlet /> : <Navigate to="/login" />;
}

export default PrivateRoute