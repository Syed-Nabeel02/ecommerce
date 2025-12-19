/**
 * BackDrop.jsx
 * Semi-transparent overlay that appears behind modals and dropdowns.
 * Creates a focus effect by dimming the background content.
 */

/**
 * BackDrop Component
 * @param {object} data - If provided, adjusts top position to account for navbar
 */
const BackDrop = ({ data }) => {
  return (
    <div
        className={`z-20 transition-all duration-150 opacity-60 w-screen h-screen bg-slate-300 fixed ${data ? "top-16" : "top-0"} left-0`}
    ></div>
  )
}

export default BackDrop
