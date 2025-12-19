/**
 * EmptyCartState.jsx
 * Empty state component shown when shopping cart has no items.
 * Displays message and button to return to shopping.
 */

// Icons for cart and back arrow
import { MdArrowBack, MdShoppingCart } from 'react-icons/md';
// React Router Link for navigation
import { Link } from 'react-router-dom';

/**
 * EmptyCartState Component
 * Shows friendly empty cart message with call-to-action to browse products
 */
const EmptyCartState = () => {
  return (
    <div className='min-h-[800px] flex flex-col items-center justify-center px-4'>
      {/* Empty Cart Icon & Message */}
      <div className='flex flex-col items-center gap-3 text-center'>
        <div className='p-4 bg-gray-100 rounded-full'>
          <MdShoppingCart size={80} className='text-gray-400' />
        </div>

        <h2 className='text-3xl lg:text-4xl font-bold text-gray-900'>Your cart is empty</h2>

        <p className='text-base lg:text-lg text-gray-600 max-w-md'>
          Add some products to get started shopping
        </p>
      </div>

      {/* Back to Shopping Link */}
      <Link
        to='/'
        className='mt-8 flex items-center gap-2 px-6 py-3 bg-red-600 text-white font-semibold rounded-lg hover:bg-red-700 transition-colors duration-200'
      >
        <MdArrowBack size={20} />
        <span>Start Shopping</span>
      </Link>
    </div>
  );
};

export default EmptyCartState;
