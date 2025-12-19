/**
 * CartItemRow.jsx
 * Individual cart item row with product details, quantity controls, and remove button.
 * Displays product image, name, price, and allows quantity adjustment.
 */

// React hook for component state
import { useState } from 'react';
// Trash icon for remove button
import { HiOutlineTrash } from 'react-icons/hi';
// Redux hook to dispatch actions
import { useDispatch } from 'react-redux';
// Quantity control component (+ and - buttons)
import QuantityControl from './QuantityControl';
// Redux actions for cart operations
import { decreaseCartItemQuantity, increaseCartItemQuantity, removeItemFromCart } from '../../../store/actions';
// Toast notifications for user feedback
import toast from 'react-hot-toast';

/**
 * CartItemRow Component
 * @param {number} productId - Product identifier
 * @param {string} productName - Product name
 * @param {string} image - Product image URL
 * @param {string} description - Product description
 * @param {number} quantity - Current quantity in cart
 * @param {number} price - Product price
 * @param {number} cartId - Cart item identifier
 */
const CartItemRow = ({ productId, productName, image, description, quantity, price, cartId }) => {
  // Track current quantity in local state for immediate UI updates
  const [currentQuantity, setCurrentQuantity] = useState(quantity);
  const dispatch = useDispatch();

  // Cart item data object for Redux actions
  const cartItemData = {
    image,
    productName,
    description,
    price,
    productId,
    quantity: currentQuantity,
  };

  // Increase product quantity in cart
  const handleIncreaseQuantity = () => {
    dispatch(increaseCartItemQuantity(cartItemData, toast, currentQuantity, setCurrentQuantity));
  };

  // Decrease product quantity in cart (minimum 1)
  const handleDecreaseQuantity = () => {
    if (currentQuantity > 1) {
      const newQuantity = currentQuantity - 1;
      setCurrentQuantity(newQuantity);
      dispatch(decreaseCartItemQuantity({ ...cartItemData, quantity: newQuantity }, newQuantity));
    }
  };

  // Remove item completely from cart
  const handleRemoveItem = () => {
    dispatch(removeItemFromCart(cartItemData, toast));
  };

  // Calculate total price for this item (quantity * price)
  const totalPrice = (currentQuantity * Number(price)).toFixed(2);
  // Truncate long product names to prevent layout issues
  const truncatedName = productName.length > 90 ? productName.substring(0, 90) + '...' : productName;

  return (
    <div className='grid md:grid-cols-5 grid-cols-4 gap-4 items-center border border-gray-200 rounded-lg p-2 lg:px-4 py-4 md:text-base text-sm bg-white hover:shadow-sm transition-shadow duration-200'>
      {/* Product Info */}
      <div className='md:col-span-2 flex flex-col gap-3'>
        <div className='flex flex-col lg:flex-row lg:gap-4 sm:gap-3 gap-2'>
          <div className='md:w-36 sm:w-24 w-16 flex-shrink-0'>
            <img
              src={image}
              alt={productName}
              className='md:h-36 sm:h-24 h-16 w-full object-cover rounded-lg'
            />
          </div>

          <div className='flex flex-col gap-2 flex-1'>
            <h3 className='lg:text-base text-sm font-semibold text-gray-900 line-clamp-2'>
              {truncatedName}
            </h3>
            <p className='text-xs lg:text-sm text-gray-600 line-clamp-1'>{description}</p>
          </div>
        </div>

        <button
          onClick={handleRemoveItem}
          type='button'
          className='flex items-center gap-2 w-fit px-3 py-1.5 text-xs font-medium text-red-600 border border-red-600 rounded-lg hover:bg-red-50 transition-colors duration-200'
          aria-label='Remove item from cart'
        >
          <HiOutlineTrash size={16} />
          Remove
        </button>
      </div>

      {/* Price */}
      <div className='justify-self-center lg:text-base text-sm font-semibold text-gray-900'>
        ${Number(price).toFixed(2)}
      </div>

      {/* Quantity Control */}
      <div className='justify-self-center'>
        <QuantityControl
          quantity={currentQuantity}
          cardCounter={true}
          onIncrease={handleIncreaseQuantity}
          onDecrease={handleDecreaseQuantity}
        />
      </div>

      {/* Total Price */}
      <div className='justify-self-center lg:text-base text-sm font-semibold text-gray-900'>
        ${totalPrice}
      </div>
    </div>
  );
};

export default CartItemRow;
