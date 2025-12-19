/**
 * SelectDropdown.jsx
 * Custom styled dropdown select component for choosing categories.
 * Uses Headless UI for accessibility and keyboard navigation.
 */

// Headless UI components for accessible dropdown menu
import { Listbox, ListboxButton, ListboxOption, ListboxOptions } from "@headlessui/react";
// Icons for check mark and dropdown arrow
import { FaCheck, FaChevronDown } from "react-icons/fa";

/**
 * SelectDropdown Component
 * @param {string} label - Label text displayed above dropdown
 * @param {object} select - Currently selected category object
 * @param {function} setSelect - Function to update selected category
 * @param {Array} lists - Array of category objects to choose from
 */
const SelectDropdown = ({
    label,
    select,
    setSelect,
    lists
}) => {
    return (
        <Listbox value={select} onChange={setSelect}>
            <div className="flex flex-col gap-2 w-full">
                <label
                    htmlFor="id"
                    className="font-semibold text-sm text-gray-900">
                    {label}
                </label>

                <div className="relative">
                    <ListboxButton
                        className="relative text-sm py-3 px-4 rounded-lg border border-gray-300 w-full cursor-default bg-white text-left text-gray-700 hover:border-red-600 focus:outline-none focus:ring-2 focus:ring-red-600 focus:ring-offset-2 transition-all duration-200 flex items-center justify-between group">
                        <span className="block truncate">
                            {select?.categoryName || 'Select an option'}
                        </span>
                        <FaChevronDown className="text-gray-400 group-hover:text-red-600 transition-colors duration-200" />
                    </ListboxButton>

                    <ListboxOptions
                        transition
                        className="absolute z-10 max-h-60 w-full overflow-auto rounded-lg bg-white py-1 text-base shadow-lg ring-1 ring-gray-300 focus:outline-hidden animate-in fade-in slide-in-from-top-2 duration-200 mt-2">

                        {lists?.map((category) => (
                            <ListboxOption
                                key={category.categoryId}
                                value={category}
                                className="group relative cursor-default py-3 pl-3 pr-9 text-gray-900 data-focus:bg-red-600 data-focus:text-white transition-colors duration-150 hover:bg-red-50">

                                <span className="block truncate font-medium group-data-selected:font-semibold">
                                    {category.categoryName}
                                </span>

                                <span className="absolute inset-y-0 right-0 flex items-center pr-4 text-red-600 group-data-focus:text-white [.group:not([data-selected])_&]:hidden">
                                    <FaCheck className="text-lg" />
                                </span>

                            </ListboxOption>
                        ))}
                    </ListboxOptions>
                </div>
            </div>
        </Listbox>
    );
};

export default SelectDropdown;
