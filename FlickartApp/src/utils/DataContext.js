import { createContext, useMemo } from "react";
import { useState } from "react";
import useGetProducts from "./customHooks/useGetProducts";

const DataContext = createContext({});

export const DataProvider = ({ children }) => {
  const [searchText, setSearchText] = useState("");
  const [offset, setOffset] = useState(0);
  const [limit, setLimit] = useState(10);
  const { loading, products, categories, hasNext,totalCount ,error  } = useGetProducts(
    limit,
    offset,
    searchText
  );
  const value = useMemo(() => ({
    searchText,
    setSearchText,
    loading,
    products,
    categories,
    error,
    offset,
    setOffset,
    limit,
    setLimit,
    hasNext,
    totalCount,
  }), [searchText, loading, products, categories, error, offset, limit, hasNext, totalCount]);
  return (
    <DataContext.Provider
      value={value}
    >
      {children}
    </DataContext.Provider>
  );
};
export default DataContext;
