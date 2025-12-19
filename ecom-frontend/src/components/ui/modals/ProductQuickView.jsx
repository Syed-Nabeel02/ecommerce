/**
 * ProductQuickView.jsx
 * Quick view modal for viewing product details without leaving the current page.
 * Displays product image, name, price, stock status, and description.
 */

// Headless UI components for accessible modal dialogs
import { Dialog, DialogBackdrop, DialogPanel, DialogTitle } from '@headlessui/react';
// Icons for stock status (checkmark for in stock, X for out of stock)
import { MdClose, MdDone } from 'react-icons/md';
// Close icon for modal
import { RxCross1 } from 'react-icons/rx';
// Material-UI divider for visual separation
import { Divider } from '@mui/material';
// Status badge component for stock availability
import StatusBadgeBadge from '../feedback/StatusBadge';

/**
 * ProductQuickView Component
 * @param {boolean} open - Controls modal visibility
 * @param {function} setOpen - Function to close the modal
 * @param {object} product - Product data object with name, price, image, etc.
 * @param {boolean} isAvailable - Whether product is in stock
 */
function ProductQuickView({ open, setOpen, product, isAvailable }) {
  // Destructure product details
  const { productName, model, image, description, price } = product;

  // Close modal handler
  const closeModal = () => setOpen(false);

  // Format price to 2 decimal places
  const formattedPrice = Number(price).toFixed(2);

  return (
    <Dialog open={open} as="div" className="relative z-10" onClose={closeModal}>
      <DialogBackdrop className="fixed inset-0 bg-gray-500/75 transition-opacity duration-500 ease-in-out data-closed:opacity-0" />

      <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div className="flex min-h-full items-center justify-center p-4">
          <DialogPanel
            transition
            className="relative transform overflow-hidden rounded-lg bg-white shadow-xl transition-all md:max-w-[620px] md:min-w-[620px] w-full data-closed:scale-95 data-closed:opacity-0 duration-300"
          >
            {/* Product Image Section */}
            {image && (
              <div className='flex justify-center items-center aspect-3/2 bg-gray-50'>
                <img
                  src={image}
                  alt={productName}
                  className="object-contain w-full h-full"
                />
              </div>
            )}

            {/* Content Section */}
            <div className='px-6 pt-8 pb-4'>

              {/* Header with Title and Close Button */}
              <div className='flex justify-between items-start gap-4 mb-6'>
                <DialogTitle as="h1" className="lg:text-3xl sm:text-2xl text-xl font-bold text-gray-900 flex-1">
                  {productName}
                </DialogTitle>
                <button
                  onClick={closeModal}
                  className='text-red-600 hover:text-red-700 transition-colors duration-200 flex-shrink-0'
                  aria-label="Close modal"
                >
                  <RxCross1 className='text-2xl' />
                </button>
              </div>

              {/* Model Info */}
              {model && (
                <p className="text-gray-600 text-sm font-semibold mb-4">
                  Model: {model}
                </p>
              )}

              <Divider className="mb-6" />

              {/* Price and Availability */}
              <div className="flex items-center justify-between gap-4 mb-6">
                <span className="text-3xl font-bold text-red-600">
                  ${formattedPrice}
                </span>

                {isAvailable ? (
                  <StatusBadgeBadge
                    text="In Stock"
                    icon={MdDone}
                    bg="bg-teal-200"
                    color="text-teal-900"
                  />
                ) : (
                  <StatusBadgeBadge
                    text="Out-Of-Stock"
                    icon={MdClose}
                    bg="bg-rose-200"
                    color="text-rose-700"
                  />
                )}
              </div>

              {/* Description */}
              <p className="text-gray-700 leading-relaxed text-base">
                {description}
              </p>
            </div>

            {/* Footer Section */}
            <div className="px-6 py-4 border-t border-gray-200 flex justify-end">
              <button
                onClick={closeModal}
                type="button"
                className="px-6 py-2 text-sm font-semibold text-gray-700 border border-gray-300 hover:bg-gray-50 hover:border-gray-400 rounded-lg transition-all duration-200"
              >
                Close
              </button>
            </div>
          </DialogPanel>
        </div>
      </div>
    </Dialog>
  );
}

export default ProductQuickView;
