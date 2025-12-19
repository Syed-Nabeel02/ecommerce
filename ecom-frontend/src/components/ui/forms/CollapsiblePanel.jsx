/**
 * CollapsiblePanel.jsx
 * Expandable/collapsible section with toggle button.
 * Used for optional form sections that can be shown or hidden.
 */

// React core library
import React from 'react';
// Chevron icons for expand/collapse indicator
import { FaChevronDown, FaChevronUp } from 'react-icons/fa';

/**
 * CollapsiblePanel Component
 * @param {string} title - Title displayed in the panel header
 * @param {boolean} isOpen - Whether the panel is currently expanded
 * @param {function} setIsOpen - Function to toggle panel open/closed
 * @param {ReactNode} children - Content to show when panel is expanded
 * @param {Component} icon - Optional icon component to show next to title
 */
const CollapsiblePanel = ({ title, isOpen, setIsOpen, children, icon: Icon }) => {
    return (
        <div className="bg-white rounded-lg border border-gray-200 shadow-sm hover:shadow-md transition-shadow duration-200 my-4 overflow-hidden">
            <button
                type="button"
                onClick={() => setIsOpen(!isOpen)}
                className="w-full flex justify-between items-center px-6 py-4 bg-gradient-to-r from-gray-50 to-gray-100 hover:from-red-50 hover:to-red-100 transition-all duration-200 group">
                <div className="flex items-center gap-3">
                    {Icon && <Icon className="text-red-600 group-hover:text-red-700 transition-colors" />}
                    <span className="font-semibold text-gray-900 group-hover:text-gray-900">{title}</span>
                    <span className="text-xs font-medium text-gray-500 bg-gray-200 px-2 py-1 rounded-full group-hover:bg-red-200 transition-colors">(Optional)</span>
                </div>
                <div className="flex items-center">
                    {isOpen ? (
                        <FaChevronUp className="text-red-600 transition-transform duration-300" />
                    ) : (
                        <FaChevronDown className="text-red-600 transition-transform duration-300" />
                    )}
                </div>
            </button>
            {isOpen && (
                <div className="p-6 border-t border-gray-200 bg-gray-50 animate-in fade-in slide-in-from-top-2 duration-200">
                    {children}
                </div>
            )}
        </div>
    );
};

export default CollapsiblePanel;
