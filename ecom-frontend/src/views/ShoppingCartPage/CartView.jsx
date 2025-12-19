/**
 * CartView.jsx
 * Shopping cart page displaying cart items with checkout functionality.
 * Shows empty state if no items, otherwise displays cart items and totals.
 * Redesigned with modern red gradient theme and improved visual hierarchy.
 */

import { MdArrowBack, MdShoppingCart, MdDelete } from 'react-icons/md';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import CartItemRow from './components/CartItemRow';
import EmptyCartState from './components/EmptyCartState';
import { syncCartForCheckout } from '../../store/actions';
import toast from 'react-hot-toast';

const CartView = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { items } = useSelector((state) => state.cart);

    // Calculate total price from all cart items
    const cartSummary = { ...items };
    cartSummary.totalPrice = items?.reduce(
        (acc, cur) => acc + Number(cur?.price) * Number(cur?.quantity), 0
    );

    // Show empty cart state if no items
    if (!items || items.length === 0) return <EmptyCartState />;

    return (
        <div className="min-h-[calc(100vh-64px)] bg-gradient-to-br from-slate-50 to-slate-100 py-8 px-4">
            <div className="container mx-auto max-w-7xl">

                {/* Page Header */}
                <div className="mb-8">
                    <div className="flex items-center gap-3 mb-2">
                        <div className="bg-red-600 p-2 rounded-lg">
                            <MdShoppingCart size={32} className="text-white" />
                        </div>
                        <div>
                            <h1 className="text-4xl font-bold text-slate-800">Shopping Cart</h1>
                            <p className="text-slate-600 text-sm mt-1">Review and manage your items</p>
                        </div>
                    </div>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">

                    {/* Left Column - Cart Items */}
                    <div className="lg:col-span-2">
                        <div className="bg-white rounded-lg shadow-lg overflow-hidden">

                            {/* Table Header */}
                            <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 py-4">
                                <div className="grid md:grid-cols-5 grid-cols-4 gap-4 font-semibold text-white">
                                    <div className="md:col-span-2 text-sm">Product</div>
                                    <div className="text-center text-sm">Price</div>
                                    <div className="text-center text-sm">Quantity</div>
                                    <div className="text-center text-sm">Total</div>
                                </div>
                            </div>

                            {/* Cart Items List */}
                            <div className="divide-y divide-slate-200">
                                {items && items.length > 0 &&
                                    items.map((item, i) => (
                                        <CartItemRow key={i} {...item}/>
                                    ))}
                            </div>

                            {/* Empty State Message */}
                            {(!items || items.length === 0) && (
                                <div className="p-8 text-center">
                                    <p className="text-slate-600 font-medium">No items in your cart</p>
                                </div>
                            )}
                        </div>

                        {/* Continue Shopping Link */}
                        <Link
                            to="/products"
                            className="flex gap-2 items-center mt-4 text-red-600 hover:text-red-700 font-semibold transition-colors"
                        >
                            <MdArrowBack size={20} />
                            <span>Continue Shopping</span>
                        </Link>
                    </div>

                    {/* Right Column - Order Summary */}
                    <div className="lg:col-span-1">
                        <div className="bg-white rounded-lg shadow-lg overflow-hidden sticky top-24">

                            {/* Summary Header */}
                            <div className="bg-gradient-to-r from-red-600 to-red-700 px-6 py-4">
                                <h2 className="text-xl font-semibold text-white flex items-center gap-2">
                                    <MdShoppingCart size={24} />
                                    Order Summary
                                </h2>
                            </div>

                            {/* Summary Content */}
                            <div className="p-6 space-y-6">

                                {/* Items Count */}
                                <div className="flex justify-between items-center pb-4 border-b border-slate-200">
                                    <span className="text-slate-700 font-medium">Items ({items?.length || 0})</span>
                                    <span className="text-lg font-semibold text-slate-800">
                                        ${Number(cartSummary?.totalPrice).toFixed(2)}
                                    </span>
                                </div>

                                {/* Pricing Breakdown */}
                                <div className="space-y-3">
                                    <div className="flex justify-between text-sm text-slate-600">
                                        <span>Subtotal</span>
                                        <span>${Number(cartSummary?.totalPrice).toFixed(2)}</span>
                                    </div>
                                    <div className="flex justify-between text-sm text-slate-600">
                                        <span>Taxes & Shipping</span>
                                        <span className="text-slate-500 text-xs">Calculated at checkout</span>
                                    </div>
                                </div>

                                {/* Total */}
                                <div className="border-t border-slate-200 pt-4">
                                    <div className="flex justify-between items-center">
                                        <span className="text-lg font-bold text-slate-800">Total</span>
                                        <span className="text-2xl font-bold text-red-600">
                                            ${Number(cartSummary?.totalPrice).toFixed(2)}
                                        </span>
                                    </div>
                                </div>

                                {/* Checkout Button */}
                                <button
                                    onClick={() => dispatch(syncCartForCheckout(navigate, toast))}
                                    className="w-full bg-gradient-to-r from-red-600 to-red-700 text-white font-semibold py-3 px-4 rounded-lg hover:shadow-lg transition-all duration-200 flex items-center justify-center gap-2 mt-6"
                                >
                                    <MdShoppingCart size={20} />
                                    Proceed to Checkout
                                </button>





                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default CartView;
