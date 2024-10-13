import React from "react";
import "../../styles/global.css";
import Loading from "../Loading";
import CategoriesBody from "./CategoriesBody";
import ProductsListBody from "./ProductsListBody";
import useGetProductsPagination from "../../utils/customHooks/useGetProductsPagination";
const HomeBody = () => {
  const {
    loading,
    products,
    categories,
    offset,
    setOffset,
    limit,
    hasNext
  } = useGetProductsPagination();


 
  return loading && offset === 0 ? <Loading/> : (
    <div>
      <CategoriesBody categories={categories}/>
      <ProductsListBody products={products} loading={loading} offset={offset} setOffset={setOffset} limit={limit} hasNext={hasNext}/>
    </div>
  );
};
export default HomeBody;
