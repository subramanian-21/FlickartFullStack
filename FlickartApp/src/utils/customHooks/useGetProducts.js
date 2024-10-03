import { useCallback, useEffect, useState } from "react";
import getProducts from "../api/getProducts";

export default function useGetProducts(limit, offset, searchText) {
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState(null);


  const fetchData = useCallback(
    async () => {
        setLoading(true);
        try {
          const response = await getProducts(limit, offset, searchText);
          console.log(response)
          setProducts(response.response?.products);
          setCategories(response.response?.categories);
          setLoading(false);
        } catch (error) {
          setError(error);
        }
    },
    []
  );

  useEffect(() => {
    fetchData();
  }, [limit, offset, searchText]);

  return { loading, products, categories, error };
}
