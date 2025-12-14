/**
 * ProductFilter.jsx
 * Product filtering and sorting component.
 * Renamed from Filter for better clarity.
 * Allows filtering by category, sorting by price, and searching by keyword.
 */

import { Button, FormControl, InputLabel, MenuItem, Select, Tooltip } from '@mui/material';
import { useEffect, useState } from 'react';
import { FiArrowDown, FiArrowUp, FiRefreshCw, FiSearch } from 'react-icons/fi';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';

const ProductFilter = ({ categories }) => {
    const [searchParams] = useSearchParams();
    const params = new URLSearchParams(searchParams);
    const pathname = useLocation().pathname;
    const navigate = useNavigate();
    
    const [category, setCategory] = useState("all");
    const [model, setModel] = useState("");
    const [sortOrder, setSortOrder] = useState("asc");
    const [sortField, setSortField] = useState("price");
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        const currentCategory = searchParams.get("category") || "all";
        const currentModel = searchParams.get("model") || "";
        const currentSortOrder = searchParams.get("sortby") || "asc";
        const currentSortField = searchParams.get("sortfield") || "price";
        const currentSearchTerm = searchParams.get("keyword") || "";

        setCategory(currentCategory);
        setModel(currentModel);
        setSortOrder(currentSortOrder);
        setSortField(currentSortField);
        setSearchTerm(currentSearchTerm);
    }, [searchParams]);

    useEffect(() => { 
        const handler = setTimeout(() => {
            if (searchTerm) {
                searchParams.set("keyword", searchTerm);
            } else {
                searchParams.delete("keyword");
            }
            navigate(`${pathname}?${searchParams.toString()}`);
        }, 700);

        return () => {
            clearTimeout(handler);
        };
    }, [searchParams, searchTerm, navigate, pathname]);

    const handleCategoryChange = (event) => {
        const selectedCategory = event.target.value;

        if (selectedCategory === "all") {
            params.delete("category");
        } else {
            params.set("category", selectedCategory);
        }
        navigate(`${pathname}?${params}`);
        setCategory(event.target.value);
    };

    const handleSortFieldChange = (event) => {
        const selectedField = event.target.value;
        params.set("sortfield", selectedField);
        navigate(`${pathname}?${params}`);
        setSortField(selectedField);
    };

    const toggleSortOrder = () => {
        setSortOrder((prevOrder) => {
            const newOrder = (prevOrder === "asc") ?  "desc" : "asc";
            params.set("sortby", newOrder);
            navigate(`${pathname}?${params}`);
            return newOrder;
        })
    };

    const handleClearFilters = () => {
        navigate({ pathname : window.location.pathname });
    };

    return (
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4">
            {/* SEARCH BAR */}
            <div className="relative flex items-center 2xl:w-[450px] sm:w-[420px] w-full">
                <input 
                    type="text"
                    placeholder="Search Products"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-hidden focus:ring-2 focus:ring-[#1976d2]"/>
                <FiSearch className="absolute left-3 text-slate-800 size={20}"/>
            </div>

            {/* CATEGORY SELECTION */}
            <div className="flex sm:flex-row flex-col gap-4 items-center">
                <FormControl
                    className="text-slate-800 border-slate-700"
                    variant="outlined"
                    size="small">
                        <InputLabel id="category-select-label">Category</InputLabel>
                        <Select
                            labelId="category-select-label"
                            value={category}
                            onChange={handleCategoryChange}
                            label="Category"
                            className="min-w-[120px] text-slate-800 border-slate-700"
                         >
                            <MenuItem value="all">All</MenuItem>
                            {categories.map((item) => (
                                <MenuItem key={item.categoryId} value={item.categoryName}>
                                    {item.categoryName}
                                </MenuItem>
                            ))}
                         </Select>
                </FormControl>

                {/* MODEL FILTER INPUT */}
                <input
                    type="text"
                    placeholder="Filter by Model"
                    value={model}
                    onChange={(e) => {
                        const value = e.target.value;
                        setModel(value);
                        if (value) {
                            params.set("model", value);
                        } else {
                            params.delete("model");
                        }
                        navigate(`${pathname}?${params}`);
                    }}
                    className="border border-gray-400 text-slate-800 rounded-md py-2 px-4 min-w-[150px] focus:outline-hidden focus:ring-2 focus:ring-[#1976d2]"
                />

                {/* SORT FIELD SELECTION */}
                <FormControl
                    className="text-slate-800 border-slate-700"
                    variant="outlined"
                    size="small">
                        <InputLabel id="sort-field-label">Sort By</InputLabel>
                        <Select
                            labelId="sort-field-label"
                            value={sortField}
                            onChange={handleSortFieldChange}
                            label="Sort By"
                            className="min-w-[140px] text-slate-800 border-slate-700"
                         >
                            <MenuItem value="price">Price</MenuItem>
                            <MenuItem value="productName">Product Name</MenuItem>
                         </Select>
                </FormControl>

                {/* SORT ORDER BUTTON & CLEAR FILTER */}
                <Tooltip title={`Toggle sort order (currently ${sortOrder === "asc" ? "ascending" : "descending"})`}>
                    <Button variant="contained"
                        onClick={toggleSortOrder}
                        color="primary"
                        className="flex items-center gap-2 h-10">
                        Order
                        {sortOrder === "asc" ? (
                            <FiArrowUp size={20} />
                        ) : (
                            <FiArrowDown size={20} />
                        )}

                    </Button>
                </Tooltip>
                <button 
                className="flex items-center gap-2 bg-rose-900 text-white px-3 py-2 rounded-md transition duration-300 ease-in shadow-md focus:outline-hidden"
                onClick={handleClearFilters}
                >
                    <FiRefreshCw className="font-semibold" size={16}/>
                    <span className="font-semibold">Clear Filter</span>
                </button>
            </div>
        </div>
    );
}

export default ProductFilter;