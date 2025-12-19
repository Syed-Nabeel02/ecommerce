/**
 * PaginationControls.jsx
 * Pagination component for navigating between pages of data.
 * Updates URL query parameters to track current page.
 */

// Material-UI Pagination component for page navigation
import { Pagination } from '@mui/material';
// React Router hooks for URL manipulation and navigation
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';

/**
 * PaginationControls Component
 * @param {number} numberOfPage - Total number of pages available
 * @param {number} totalProducts - Total number of items (not currently used)
 */
const PaginationControls = ({ numberOfPage, totalProducts }) => {
    // Get URL search parameters (e.g., ?page=2)
    const [searchParams] = useSearchParams();
    // Get current URL path
    const pathname = useLocation().pathname;
    // Create new URLSearchParams object for manipulation
    const params = new URLSearchParams(searchParams);
    const navigate = useNavigate();

    // Get current page from URL parameter, default to 1 if not present
    const paramValue = searchParams.get("page")
                ? Number(searchParams.get("page"))
                : 1;

    // Handle page change - update URL with new page number
    const onChangeHandler = (event, value) => {
        params.set("page", value.toString());
        navigate(`${pathname}?${params}`);
    };

    return(
        <Pagination 
            count={numberOfPage} 
            page={paramValue}
            defaultPage={1} 
            siblingCount={0} 
            boundaryCount={2} 
            shape="rounded" 
            onChange = {onChangeHandler}
            />
    )
};

export default PaginationControls;