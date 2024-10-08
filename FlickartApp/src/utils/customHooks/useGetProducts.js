import { useCallback, useEffect, useState } from "react";
import getProducts from "../api/getProducts";

export default function useGetProducts(limit, offset, searchText) {
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState(null);
  const [hasNext , setHasNext] = useState(false);

  const fetchData = useCallback(
    async () => {
        setLoading(true);
        try {
          const response = await getProducts(limit, offset, searchText);
          setProducts((pro)=> [...pro,...response.response?.products]);
          setCategories(response?.response?.categories);
          setLoading(false);
          setHasNext(response?.response?.hasNext);
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

  return { loading, products, categories, hasNext ,error };
}
