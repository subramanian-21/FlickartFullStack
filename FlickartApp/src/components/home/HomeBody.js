import React, { useContext } from "react";
import "../../styles/global.css";
import DataContext from "../../utils/DataContext";
import Loading from "../Loading";
import CategoriesBody from "./CategoriesBody";
import ProductsListBody from "./ProductsListBody";
const HomeBody = () => {
  const {
    loading,
    products,
    categories,
    offset,
    setOffset,
    limit,
    hasNext
  } = useContext(DataContext);


 
  return loading && offset === 0 ? <Loading/> : (
    <div>
      <CategoriesBody categories={categories}/>
      <ProductsListBody products={products} loading={loading} offset={offset} setOffset={setOffset} limit={limit} hasNext={hasNext}/>
    </div>
  );
};
export default HomeBody;
