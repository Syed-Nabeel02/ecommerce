import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useSearchParams } from "react-router-dom";
import { loadDashboardCatalog, loadCatalogItems } from "../store/actions";

const useProductFilter = () => {
    const [searchParams] = useSearchParams();
    const dispatch = useDispatch();

    useEffect(() => {
        const params = new URLSearchParams();

        const currentPage = searchParams.get("page")
            ? Number(searchParams.get("page"))
            : 1;

        params.set("pageNumber", currentPage - 1);

        const sortOrder = searchParams.get("sortby") || "asc";
        const sortField = searchParams.get("sortfield") || "price";
        const categoryParams = searchParams.get("category") || null;
        const keyword = searchParams.get("keyword") || null;
        const model = searchParams.get("model") || null;
        params.set("sortBy", sortField);
        params.set("sortOrder", sortOrder);

        if (categoryParams) {
            params.set("category", categoryParams);
        }

        if (keyword) {
            params.set("keyword", keyword);
        }

        if (model) {
            params.set("model", model);
        }

        const queryString = params.toString();
        console.log("QUERY STRING", queryString);
        
        dispatch(loadCatalogItems(queryString));

    }, [dispatch, searchParams]);
};


export const useDashboardProductFilter = () => {
    const [searchParams] = useSearchParams();
    const dispatch = useDispatch();

    useEffect(() => {
        const params = new URLSearchParams();

        const currentPage = searchParams.get("page")
            ? Number(searchParams.get("page"))
            : 1;

        params.set("pageNumber", currentPage - 1);

        const queryString = params.toString();
        dispatch(loadDashboardCatalog(queryString));

    }, [dispatch, searchParams]);
};

export default useProductFilter;