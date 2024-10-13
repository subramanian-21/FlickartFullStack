import React, { useEffect, useState } from 'react'
import useGetCategoryProducts from '../utils/customHooks/useGetCategoryProducts';
import ProductsListBody from '../components/home/ProductsListBody';
import { useParams } from 'react-router-dom';
import CategoriesBody from '../components/home/CategoriesBody';
import useGetProductsPagination from '../utils/customHooks/useGetProductsPagination';

export default function Category() {
    const [limit, setLimit] = useState(10);
    const [offset, setOffset] = useState(0);
    const [category, setCategory] = useState("");
    const {categories} = useGetProductsPagination();
    const {loading, products, hasNext} = useGetCategoryProducts(limit, offset, category);
    const { categoryId } = useParams();
    console.log(categoryId);
    useEffect(() => {
        setCategory(categoryId);
    },[categoryId])

  return (
    <>
    <CategoriesBody categories={categories}/>
    <ProductsListBody products={products} loading={loading} offset={offset} setOffset={setOffset} limit={limit} hasNext={hasNext}/>
    </>
  )
}
