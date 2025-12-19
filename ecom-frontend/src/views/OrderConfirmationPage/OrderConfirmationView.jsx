/**
 * OrderConfirmationView.jsx
 * Order confirmation page displayed after successful checkout.
 * Shows success message and links to continue shopping or view profile.
 */

// React core library
import React from 'react'
// Icons for checkmark, home, and shopping bag
import { MdCheckCircle, MdHome, MdShoppingBag } from 'react-icons/md';
// React Router Link for navigation
import { Link } from 'react-router-dom'

/**
 * OrderConfirmationView Component
 * Success page shown after placing an order
 */
const OrderConfirmationView = () => {
  return (
    <div className="min-h-[calc(100vh-64px)] bg-gradient-to-br from-slate-50 to-slate-100 flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        {/* Card container */}
        <div className="bg-white shadow-lg rounded-lg overflow-hidden">
          {/* Success Header */}
          <div className="bg-gradient-to-r from-green-500 to-green-600 px-6 sm:px-8 py-12 flex flex-col items-center justify-center space-y-4">
            <div className="bg-white bg-opacity-20 p-4 rounded-full animate-bounce">
              <MdCheckCircle className="text-white text-6xl" />
            </div>
            <h1 className="text-white text-center font-montserrat text-3xl sm:text-4xl font-bold">
              Order Placed!
            </h1>
            <p className="text-green-100 text-sm text-center max-w-xs">
              Your order has been successfully confirmed
            </p>
          </div>

          {/* Content Section */}
          <div className="px-6 sm:px-8 py-8 space-y-6">
            {/* Confirmation Details */}
            <div className="bg-green-50 border-l-4 border-green-500 p-4 rounded">
              <p className="text-green-800 font-semibold mb-2">✓ Confirmation Details</p>
              <p className="text-green-700 text-sm">
                Thank you for your order! We're processing it and you'll receive a confirmation email shortly with your order details and tracking information.
              </p>
            </div>

            {/* Order Info Boxes */}

              <div className="bg-slate-50 p-4 rounded-lg border-2 border-slate-200">
                <p className="text-xs text-slate-600 font-semibold mb-1">STATUS</p>
                <p className="text-lg font-bold text-green-600">ACCEPTED</p>
              </div>


            {/* Next Steps */}
            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
              <p className="text-sm text-blue-900 font-semibold mb-2">📧 What's Next?</p>
              <ul className="text-sm text-blue-800 space-y-1">
                <li>• Our team will contact you shortly</li>
              </ul>
            </div>

            {/* Action Buttons */}
            <div className="space-y-3 pt-4">
              <Link
                to="/products"
                className="w-full bg-gradient-to-r from-red-600 to-red-700 text-white font-semibold py-3 rounded-lg hover:shadow-lg transition-all duration-200 flex items-center justify-center gap-2"
              >
                <MdShoppingBag className="text-lg" />
                Continue Shopping
              </Link>
              <Link
                to="/"
                className="w-full bg-slate-200 text-slate-800 font-semibold py-3 rounded-lg hover:bg-slate-300 transition-all duration-200 flex items-center justify-center gap-2"
              >
                <MdHome className="text-lg" />
                Go to Home
              </Link>
            </div>



          </div>
        </div>

        {/* Additional Help Text Not Implemented Yet*/}
        <p className="text-center text-xs text-slate-500 mt-6">
          Order confirmation has been sent to your registered email address
        </p>
      </div>
    </div>
  )
}

export default OrderConfirmationView