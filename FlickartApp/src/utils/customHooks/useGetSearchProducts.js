import { useCallback, useEffect, useState } from "react";
import getProducts from "../api/getProducts";

export default function useGetSearchProducts(limit, offset, searchText) {
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState(null);
  const [hasNext , setHasNext] = useState(false);
  const [totalCount, setTotalCount] = useState(0);


  const fetchData = useCallback(
    async () => {
        setLoading(true);
        try {
          const response = await getProducts(limit, offset, searchText);
          console.log(response)
          setProducts(response.response?.products);
          setCategories(response.response?.categories);
          setLoading(false);
          setHasNext(response.response?.hasNext);
          setTotalCount(response.response?.totalCount);
        } catch (error) {
          setError(error);
          setLoading(false);
        }
    },
    [limit, offset, searchText]
  );

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  return { loading, products, categories, hasNext, totalCount ,error };
}
