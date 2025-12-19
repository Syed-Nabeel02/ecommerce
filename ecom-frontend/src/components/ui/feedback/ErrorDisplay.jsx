/**
 * ErrorDisplay.jsx
 * Error message display component with icon and centered layout.
 * Shows user-friendly error messages when something goes wrong.
 */

// React core library
import React from 'react';
// Warning triangle icon from react-icons
import { FaExclamationTriangle } from 'react-icons/fa';

/**
 * ErrorDisplay Component
 * @param {string} message - Error message to display to the user
 */
const ErrorDisplay = ({ message }) => {
  return (
    <div className='flex flex-col items-center justify-center px-6 py-16 bg-gradient-to-br from-gray-50 to-gray-100 rounded-lg border border-gray-200'>
      <div className='bg-red-50 p-4 rounded-full mb-6'>
        <FaExclamationTriangle className='text-red-600 text-5xl' />
      </div>

      <h2 className='text-2xl font-bold text-gray-900 mb-3'>
        Something Went Wrong
      </h2>

      <p className='text-gray-600 text-center max-w-md mb-6 leading-relaxed'>
        {message || 'An unexpected error has occurred.'}
      </p>


    </div>
  );
};

export default ErrorDisplay;
