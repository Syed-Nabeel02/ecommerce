/**
 * TopNavBar.jsx
 * Main navigation bar displayed at the top of every page.
 * Shows logo, navigation links, cart with item count, and user menu.
 * Responsive design with collapsible menu on mobile.
 */

// React hook for component state
import { useState } from 'react';
// Redux hook to access global state (cart items, current user)
import { useSelector } from 'react-redux';
// React Router for navigation and getting current URL
import { Link, useLocation } from 'react-router-dom';
// Material-UI badge component for cart item count
import { Badge } from '@mui/material';
// Icons for cart, login, and store logo
import { FaShoppingCart, FaSignInAlt, FaShoppingBag } from 'react-icons/fa';
// Menu icons for mobile hamburger menu
import { IoIosMenu } from 'react-icons/io';
import { RxCross2 } from 'react-icons/rx';
// User profile dropdown menu component
import UserMenu from '../../UserMenu';

/**
 * TopNavBar Component
 * Main navigation bar for the entire application
 */
const TopNavBar = () => {
  // Get current page URL path to highlight active nav link
  const currentPath = useLocation().pathname;
  // Track whether mobile menu is expanded or collapsed
  const [isMenuExpanded, setIsMenuExpanded] = useState(false);
  // Get cart items from Redux store
  const { items: cartItems } = useSelector((state) => state.cart);
  // Get currently logged in user from Redux store
  const { currentUser: authenticatedUser } = useSelector((state) => state.authentication);

  // Toggle mobile menu open/closed
  const toggleMenu = () => setIsMenuExpanded(!isMenuExpanded);
  // Close mobile menu
  const closeMenu = () => setIsMenuExpanded(false);

  // Count total items in cart
  const cartItemCount = cartItems?.length || 0;
  // Check if user is logged in
  const isUserLoggedIn = authenticatedUser && authenticatedUser.id;

  // Apply different styles for active vs inactive nav links
  const getNavLinkClass = (isActive) =>
    `${isActive ? "text-white font-semibold" : "text-gray-200"}`;

  // Dynamic classes for mobile menu (expands/collapses based on state)
  const navMenuClass = `flex sm:gap-10 gap-4 sm:items-center text-slate-800 sm:static absolute left-0 top-[70px] sm:shadow-none shadow-md ${
    isMenuExpanded ? "h-fit sm:pb-0 pb-5" : "h-0 overflow-hidden"
  } transition-all duration-100 sm:h-fit sm:bg-none bg-custom-gradient text-white sm:w-fit w-full sm:flex-row flex-col px-4 sm:px-0`;

  return (
    <div className="h-[70px] bg-custom-gradient text-white z-50 flex items-center sticky top-0">
      <div className="lg:px-14 sm:px-8 px-4 w-full flex justify-between">

        {/* Logo */}
        <Link to="/" className="flex items-center text-2xl font-extrabold gap-2">
          <FaShoppingBag className="text-3xl" />
          <span className="font-extrabold leading-tight">York Store</span>
        </Link>

        {/* Navigation Menu */}
        <ul className={navMenuClass}>

          {/* Home Link */}
          <li className="font-medium transition-all duration-150">
            <Link
              className={getNavLinkClass(currentPath === "/")}
              to="/"
              onClick={closeMenu}
            >
              Home
            </Link>
          </li>

          {/* Products Link */}
          <li className="font-medium transition-all duration-150">
            <Link
              className={getNavLinkClass(currentPath === "/products")}
              to="/products"
              onClick={closeMenu}
            >
              Products
            </Link>
          </li>

          {/* Cart Link */}
          <li className="font-medium transition-all duration-150">
            <Link
              className={getNavLinkClass(currentPath === "/cart")}
              to="/cart"
              onClick={closeMenu}
            >
              <Badge
                showZero
                badgeContent={cartItemCount}
                color="primary"
                overlap="circular"
                anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
              >
                <FaShoppingCart size={25} />
              </Badge>
            </Link>
          </li>

          {/* User Authentication */}
          {isUserLoggedIn ? (
            <li className="font-medium transition-all duration-150">
              <UserMenu />
            </li>
          ) : (
            <li className="font-medium transition-all duration-150">
              <Link
                className="flex items-center space-x-2 px-4 py-[6px] bg-red-600 hover:bg-red-700 text-white font-semibold rounded-md shadow-lg transition duration-300 ease-in-out"
                to="/login"
                onClick={closeMenu}
              >
                <FaSignInAlt />
                <span>Login</span>
              </Link>
            </li>
          )}
        </ul>

        {/* Mobile Menu Toggle */}
        <button
          onClick={toggleMenu}
          className="sm:hidden flex items-center"
          aria-label="Toggle navigation menu"
        >
          {isMenuExpanded ? (
            <RxCross2 className="text-white text-3xl" />
          ) : (
            <IoIosMenu className="text-white text-3xl" />
          )}
        </button>
      </div>
    </div>
  );
};

export default TopNavBar;
