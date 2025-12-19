/**
 * ProductCard.jsx
 * Product card component displaying product info with add to cart and quick view features.
 * Shows product image, name, price, stock status, and action buttons.
 */

// React hooks for state management
import { useState } from 'react';
// Icons for shopping cart and eye (quick view)
import { FaShoppingCart, FaEye } from 'react-icons/fa';
// Modal for viewing product details
import ProductQuickView from '../modals/ProductQuickView';
// Redux hook to dispatch actions
import { useDispatch } from 'react-redux';
// Redux action to add product to cart
import { addItemToCart } from '../../../store/actions';
// Toast notifications for user feedback
import toast from 'react-hot-toast';

/**
 * ProductCard Component
 * @param {number} productId - Unique product identifier
 * @param {string} productName - Name of the product
 * @param {string} model - Product model number
 * @param {string} image - Product image URL
 * @param {string} description - Product description
 * @param {number} quantity - Available stock quantity
 * @param {number} price - Product price
 * @param {boolean} about - If true, hides action buttons (for about/info pages)
 */
const ProductCard = ({
        productId,
        productName,
        model,
        image,
        description,
        quantity,
        price,
        about = false,
}) => {
    // Track whether quick view modal is open
    const [openProductViewModal, setOpenProductViewModal] = useState(false);
    // Track if mouse is hovering over card (for quick view overlay)
    const [isHovered, setIsHovered] = useState(false);
    const btnLoader = false;
    // Store selected product data for quick view modal
    const [selectedViewProduct, setSelectedViewProduct] = useState("");
    // Check if product is in stock
    const isAvailable = quantity && Number(quantity) > 0;
    const dispatch = useDispatch();

    // Open quick view modal with product details
    const handleProductView = (product) => {
        if (!about) {
            setSelectedViewProduct(product);
            setOpenProductViewModal(true);
            setIsHovered(false);
        }
    };

    // Add product to shopping cart
    const addToCartHandler = (cartItems) => {
        dispatch(addItemToCart(cartItems, 1, toast));
    };

    return (
        <div
            className="group relative bg-white rounded-2xl shadow-lg hover:shadow-2xl overflow-hidden transition-all duration-300 transform hover:-translate-y-1"
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            {/* Stock Badge */}
            {!isAvailable && (
                <div className="absolute top-4 right-4 z-10 bg-red-700 text-white px-3 py-1 rounded-full text-s font-bold">
                    Out of Stock
                </div>
            )}

            {/* Image Container  */}
            <div
                onClick={() => {
                    handleProductView({
                        id: productId,
                        productName,
                        model,
                        image,
                        description,
                        quantity,
                        price,
                    });
                }}
                className="relative w-full h-80 bg-gray-50 flex items-center justify-center overflow-hidden cursor-pointer"
            >
                <img
                    className="max-w-full max-h-full object-contain p-4 transition-transform duration-500 group-hover:scale-110"
                    src={image}
                    alt={productName}
                />

                {/* Quick View Overlay */}
                {!about && (
                    <div className={`absolute inset-0 bg-black/40 flex items-center justify-center transition-opacity duration-300 ${isHovered ? 'opacity-100' : 'opacity-0'}`}>
                        <button className="bg-white text-gray-800 px-6 py-2 rounded-full font-semibold flex items-center gap-2 hover:bg-gray-100 transition-colors">
                            <FaEye />
                            Quick View
                        </button>
                    </div>
                )}
            </div>

            {/* Content - Smaller */}
            <div className="p-4">
                <h2
                    onClick={() => {
                        handleProductView({
                            id: productId,
                            productName,
                            model,
                            image,
                            description,
                            quantity,
                            price,
                        });
                    }}
                    className="text-base font-bold mb-1 cursor-pointer text-gray-800 hover:text-red-600 transition-colors line-clamp-2 min-h-[2.5rem]"
                >
                    {productName}
                </h2>

                {model && (
                    <p className="text-gray-600 text-s font-semibold mb-1">
                        Model: {model}
                    </p>
                )}

                <p className="text-gray-500 text-xs mb-3 line-clamp-1">
                    {description}
                </p>

                {/* Stock Info */}
                <div className="mb-3">
                    {isAvailable ? (
                        <div className="flex items-center gap-2">
                            <div className="w-2 h-2 bg-green-500 rounded-full animate-pulse"></div>
                            <span className="text-xs text-gray-600">
                                {quantity} in stock
                            </span>
                            {quantity < 10 && (
                                <span className="text-xs text-red-600 font-semibold">
                                    • Hurry up!
                                </span>
                            )}
                        </div>
                    ) : (
                        <div className="flex items-center gap-2">
                            <div className="w-2 h-2 bg-red-500 rounded-full"></div>
                            <span className="text-xs text-red-600 font-semibold">Unavailable</span>
                        </div>
                    )}
                </div>

                {!about && (
                    <div className="flex items-end justify-between pt-3 border-t border-gray-100">
                        {/* Price */}
                        <div className="flex flex-col">
                            <span className="text-xl font-extrabold text-gray-800">
                                ${Number(price).toFixed(2)}
                            </span>
                        </div>

                        {/* Add to Cart Button */}
                       <button
                           disabled={!isAvailable || btnLoader}
                           onClick={() =>
                               addToCartHandler({
                                   image,
                                   productName,
                                   description,
                                   price,
                                   productId,
                                   quantity,
                               })
                           }
                           className={`${
                               isAvailable
                                   ? "bg-red-600 hover:bg-red-700"
                                   : "bg-gray-300 cursor-not-allowed"
                           } text-white py-3 px-4 rounded-xl font-semibold flex items-center gap-2 transition-all duration-300 shadow-md hover:shadow-lg text-base`}
                       >
                           <FaShoppingCart className="text-lg" />
                           <span className="hidden sm:inline">
                               {isAvailable ? "Add" : "Unavailable"}
                           </span>
                       </button>


                    </div>
                )}
            </div>

            <ProductQuickView
                open={openProductViewModal}
                setOpen={setOpenProductViewModal}
                product={selectedViewProduct}
                isAvailable={isAvailable}
            />
        </div>
    );
};

export default ProductCard;
