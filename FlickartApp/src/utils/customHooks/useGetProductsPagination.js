import React, { useState } from 'react'
import useGetProducts from './useGetProducts';

export default function useGetProductsPagination() {
    const [searchText, setSearchText] = useState("");
    const [offset, setOffset] = useState(0);
    const [limit, setLimit] = useState(10);
    const { loading, products, categories, hasNext,totalCount ,error  } = useGetProducts(
      limit,
      offset,
      searchText
    );
    return { loading, products, categories, error, offset, setOffset, limit, setLimit, hasNext,totalCount };
}
