/**
 * StatusBadge.jsx
 * Reusable badge component for displaying status with icon (e.g., order status, payment status).
 * Customizable background color and text color.
 */

/**
 * StatusBadge Component
 * @param {string} text - Status text to display
 * @param {Component} icon - React icon component to display
 * @param {string} bg - Tailwind background color class
 * @param {string} color - Tailwind text color class
 */
const StatusBadge = ({text, icon:Icon, bg, color}) => {
    return (
        <div
            className={`${bg} ${color} px-2 py-2 font-medium rounded-sm flex items-center gap-1`}>
            {text} <Icon size={15} />
        </div>
    )
};

export default StatusBadge;