/**
 * SidebarNav.jsx
 * Sidebar navigation for admin dashboard pages.
 * Displays navigation links with icons and highlights the current page.
 */

// React core library
import React from 'react';
// Dashboard icon for sidebar header
import { FaTachometerAlt } from 'react-icons/fa';
// Redux hook to access current user
import { useSelector } from 'react-redux';
// React Router for navigation links and current URL
import { Link, useLocation } from 'react-router-dom';
// Navigation configuration (list of admin pages)
import { adminNavigation } from '../../../utilities/navigationConfig';
// Utility for conditional CSS classes
import classNames from 'classnames';

/**
 * SidebarNav Component
 * @param {boolean} isProfileLayout - Whether this sidebar is for profile (not currently used)
 */
const SidebarNav = ({isProfileLayout = false}) => {
    // Get current URL path to highlight active link
    const pathName = useLocation().pathname;
    // Get logged in user info
    const { currentUser } = useSelector((state) => state.authentication);

    // Check if user has admin role
    const isAdmin = currentUser && currentUser?.roles?.includes("ROLE_ADMIN");

    // Use admin navigation links
    const sideBarLayout = adminNavigation;
    
  return (
    <div className='flex grow flex-col gap-y-7 overflow-y-auto bg-custom-gradient px-6 pb-4'>
        <div className='flex h-16 shrink-0 gap-x-3 pt-2'>
            <FaTachometerAlt className='h-8 w-8 text-red-100'/>
            <h1 className='text-white text-xl font-bold'>
                Admin Panel
            </h1>
        </div>
        <nav className='flex flex-1 flex-col'>
            <ul role='list' className='flex flex-1 flex-col gap-y-7'>
                <li>
                    <ul role='list' className='-mx-2 space-y-4'>
                        {sideBarLayout.map((item) => (
                            <li key={item.name}>
                                <Link
                                    to={item.href}
                                    className={classNames(
                                        pathName === item.href
                                            ? "bg-red-100 text-custom-blue"
                                            : "text-gray-300 hover:bg-red-900 hover:text-white",
                                        "group flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6"
                                    )}>

                                        <item.icon className='text-2xl'/>
                                        {item.name}
                                </Link>
                            </li>
                        ))}
                    </ul>
                </li>
            </ul>
        </nav>
    </div>
  )
}

export default SidebarNav