/**
 * OrderSummaryPanel.jsx
 * Final order review panel showing billing, payment, items, and totals.
 * Redesigned with red color scheme matching auth pages.
 */

import React from 'react';
import { MdLocationOn, MdCreditCard, MdShoppingCart } from 'react-icons/md';

const OrderSummaryPanel = ({ totalPrice, cart, address, paymentMethod, paymentCard }) => {
  // Calculate subtotal from cart items
  const calculateSubtotal = () => {
    if (!cart || cart.length === 0) return 0;
    return cart.reduce((total, item) => {
      return total + Number(item.price) * Number(item.quantity);
    }, 0);
  };

  const subtotal = calculateSubtotal();

  return (
    <div className="min-h-[calc(100vh-64px)] bg-gradient-to-br from-slate-50 to-slate-100 py-8 px-4">
      <div className="container mx-auto max-w-6xl">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-slate-800 flex items-center gap-3">
            <MdShoppingCart className="text-red-600" />
            Order Summary
          </h1>
          <p className="text-slate-600 mt-2">Review your order details before confirming</p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left Column - Details */}
          <div className="lg:col-span-2 space-y-6">

            {/* Billing Address Card */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 py-4 flex items-center gap-3">
                <MdLocationOn className="text-white text-2xl" />
                <h2 className="text-xl font-semibold text-white">Billing Address</h2>
              </div>
              <div className="p-6">
                {address ? (
                  <div className="space-y-3">
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <p className="text-sm text-slate-600 font-medium">Building Name</p>
                        <p className="text-slate-800 font-semibold">{address.buildingName || 'N/A'}</p>
                      </div>
                      <div>
                        <p className="text-sm text-slate-600 font-medium">Street</p>
                        <p className="text-slate-800 font-semibold">{address.street || 'N/A'}</p>
                      </div>
                    </div>
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <p className="text-sm text-slate-600 font-medium">City</p>
                        <p className="text-slate-800 font-semibold">{address.city || 'N/A'}</p>
                      </div>
                      <div>
                        <p className="text-sm text-slate-600 font-medium">State</p>
                        <p className="text-slate-800 font-semibold">{address.state || 'N/A'}</p>
                      </div>
                    </div>
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <p className="text-sm text-slate-600 font-medium">Postal Code</p>
                        <p className="text-slate-800 font-semibold">{address.pincode || 'N/A'}</p>
                      </div>
                      <div>
                        <p className="text-sm text-slate-600 font-medium">Country</p>
                        <p className="text-slate-800 font-semibold">{address.country || 'N/A'}</p>
                      </div>
                    </div>
                  </div>
                ) : (
                  <div className="bg-red-50 border-l-4 border-red-600 p-4">
                    <p className="text-red-700 font-semibold">
                      ⚠️ No address selected. Please go back and select an address.
                    </p>
                  </div>
                )}
              </div>
            </div>

            {/* Payment Method Card */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 py-4 flex items-center gap-3">
                <MdCreditCard className="text-white text-2xl" />
                <h2 className="text-xl font-semibold text-white">Payment Method</h2>
              </div>
              <div className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm text-slate-600 font-medium mb-1">Selected Method</p>
                    <p className="text-lg font-semibold text-slate-800">
                      {paymentMethod === 'SAVED_CARD' && paymentCard
                        ? `💳 Card ending in ${paymentCard.cardNumber?.slice(-4)}`
                        : paymentMethod === 'NEW_CARD'
                        ? '💳 New Card'
                        : paymentMethod === 'COD'
                        ? '🚚 Cash on Delivery'
                        : '⚠️ Not selected'}
                    </p>
                  </div>
                  <div className="text-4xl">
                    {paymentMethod === 'COD' ? '🚚' : '💳'}
                  </div>
                </div>
                {(paymentMethod === 'SAVED_CARD' || paymentMethod === 'NEW_CARD') && (
                  <div className="mt-4 bg-blue-50 border border-blue-200 rounded p-3">
                    <p className="text-sm text-blue-700">
                      ℹ️ Payment will be processed on delivery
                    </p>
                  </div>
                )}
              </div>
            </div>

            {/* Order Items Card */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden">
              <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 py-4 flex items-center gap-3">
                <MdShoppingCart className="text-white text-2xl" />
                <h2 className="text-xl font-semibold text-white">Order Items</h2>
              </div>
              <div className="p-6">
                <div className="space-y-4">
                  {cart?.length > 0 ? (
                    cart.map((item) => (
                      <div key={item?.productId} className="flex gap-4 pb-4 border-b last:border-b-0">
                        <img
                          src={item?.image}
                          alt={item?.productName}
                          className="w-20 h-20 rounded-lg object-cover border-2 border-slate-200"
                        />
                        <div className="flex-1">
                          <p className="font-semibold text-slate-800 text-lg">{item?.productName}</p>
                          <p className="text-sm text-slate-600 mt-1">
                            Qty: <span className="font-semibold">{item?.quantity}</span> × ${Number(item?.price).toFixed(2)}
                          </p>
                        </div>
                        <div className="text-right">
                          <p className="text-lg font-bold text-red-600">
                            ${(Number(item?.quantity) * Number(item?.price)).toFixed(2)}
                          </p>
                        </div>
                      </div>
                    ))
                  ) : (
                    <p className="text-slate-600 text-center py-4">No items in cart</p>
                  )}
                </div>
              </div>
            </div>
          </div>

          {/* Right Column - Summary */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-lg shadow-lg overflow-hidden sticky top-24">
              <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 py-4">
                <h2 className="text-xl font-semibold text-white">Total Summary</h2>
              </div>
              <div className="p-6 space-y-4">
                <div className="flex justify-between items-center">
                  <span className="text-slate-700 font-medium">Subtotal</span>
                  <span className="text-lg font-semibold text-slate-800">${subtotal.toFixed(2)}</span>
                </div>

                <div className="border-t border-slate-200 pt-4">
                  <div className="flex justify-between items-center">
                    <span className="text-xl font-bold text-slate-800">Total</span>
                    <span className="text-2xl font-bold text-red-600">${subtotal.toFixed(2)}</span>
                  </div>
                </div>



              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrderSummaryPanel;
