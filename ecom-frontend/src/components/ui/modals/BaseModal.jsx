/**
 * BaseModal.jsx
 * Reusable slide-in modal component that appears from the right side.
 * Used for forms and detailed content views throughout the app.
 */

// Headless UI components for accessible modal dialogs
import { Dialog, DialogBackdrop, DialogPanel, DialogTitle } from '@headlessui/react';
// Close icon for modal header
import { RxCross1 } from 'react-icons/rx';

/**
 * BaseModal Component
 * @param {boolean} open - Controls modal visibility
 * @param {function} setOpen - Function to close the modal
 * @param {ReactNode} children - Content to display inside the modal
 * @param {string} title - Modal title displayed in header
 */
function BaseModal({ open, setOpen, children, title = "" }) {
  return (
    <>
      {/* Dialog component from Headless UI - manages modal state and accessibility */}
      <Dialog open={open} onClose={() => setOpen(false)} className="relative z-10">

        {/* Backdrop - dark overlay behind the modal */}
        {/* Covers entire screen and fades in/out based on modal state */}
        <DialogBackdrop
          className="fixed inset-0 bg-gray-500/75 transition-opacity duration-500 ease-in-out data-closed:opacity-0"
        />

        {/* Container for the modal panel - covers full screen */}
        <div className="fixed inset-0 overflow-hidden">
          <div className='absolute inset-0 overflow-hidden'>

            {/* Positioning wrapper - aligns modal to the right side */}
            <div className='pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10'>

              {/* The actual modal panel that slides in from right */}
              {/* max-w-[800px] limits panel width to 800 pixels */}
              {/* data-closed:translate-x-full makes it slide off-screen when closing */}
              <DialogPanel
                transition
                className='pointer-events-auto relative w-screen max-w-[800px] transform transition duration-500 ease-in-out data-closed:translate-x-full sm:duration-700'
              >
                {/* Inner container - white background with shadow */}
                {/* overflow-y-scroll allows content to scroll if too long */}
                <div className='flex h-full flex-col overflow-y-scroll bg-white shadow-xl'>

                  {/* Header section (currently hidden but available for future use) */}
                  <div className='px-4 sm:px-6'>
                    <DialogTitle className='text-base font-semibold leading-6 text-gray-900'>
                      Panel Title
                    </DialogTitle>
                  </div>

                  {/* Main content area */}
                  <div className='relative mt-6 flex-1 p-8'>

                    {/* Title bar with close button */}
                    {/* border-b creates bottom border, pb-8 adds padding below */}
                    <div className='border-b border-red-200 pb-8 flex justify-between'>

                      {/* Modal title text */}
                      <h1 className='font-montserrat font-bold text-red-600 text-2xl pt-4'>
                        {title}
                      </h1>

                      {/* Close button - clicking sets open to false */}
                      <button
                        onClick={() => setOpen(false)}
                        className='text-red-600 hover:text-red-700 transition-colors duration-200'
                      >
                        <RxCross1 className='text-2xl'/>
                      </button>
                    </div>

                    {/* Children content - this is where form fields, text, etc. will appear */}
                    {children}
                  </div>
                </div>
              </DialogPanel>
            </div>
          </div>
        </div>
      </Dialog>
    </>
  )
}

export default BaseModal;