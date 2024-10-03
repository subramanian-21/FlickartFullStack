import { createContext } from "react";
import { useState } from "react";
import useGetProducts from "./customHooks/useGetProducts";

const DataContext = createContext({});

export const DataProvider = ({ children }) => {
  const [searchText, setSearchText] = useState("");
  const [offset, setOffset] = useState(0);
  const [limit, setLimit] = useState(10);
  const { loading, products, categories, error } = useGetProducts(
    limit,
    offset,
    searchText
  );
  return (
    <DataContext.Provider
      value={{
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
      }}
    >
      {children}
    </DataContext.Provider>
  );
};
export default DataContext;
