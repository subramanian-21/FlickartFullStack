import { useCallback, useEffect, useState } from "react";
import { getProductsByCategory } from "../api/getProducts";

export default function useGetCategoryProducts(limit, offset, category) {
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState(null);
  const [hasNext , setHasNext] = useState(false);

  const fetchData = useCallback(
    async () => {
        if(category != ""){
            setLoading(true);
        try {
          const response = await getProductsByCategory(limit, offset, category);
        setProducts((pro)=> [...pro,...response.response?.products]);
          setLoading(false);
          setHasNext(response?.response?.hasNext);
        } catch (error) {
          setError(error);
          setLoading(false);
        }
        }else {
            setProducts([])
        }
        
    },
    [limit, offset, category]
  );

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  return { loading, products,categories, hasNext ,error };
}
