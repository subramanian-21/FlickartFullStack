import React from 'react'
import Product, { discountedLabel } from '../Product';
import Shimmer from '../Shimmer';
import LoadingLine from './LoadingLine';
import { useInfiniteScroll } from '../../utils/customHooks/useInfiniteScroll';
const DiscountProduct = discountedLabel(Product);

export default function ProductsListBody({products, loading, offset, setOffset, limit,hasNext}) {
    const fetchMoreItems = () =>{
        if(hasNext) {
          console.log("fetching next batch", offset);
          setOffset(offset+limit)
        }
      }
    const lastElementRef = useInfiniteScroll({
        onLoadMore: fetchMoreItems,
        hasMore : hasNext,
      })
  return (
    <>
         <div className=" h-500px  bg-zinc-800 m-3 flex gap-2 flex-wrap justify-center p-3">
      {products.length > 0 ? (
          products?.map((k, i) =>
            k.discount > 15 ? (
            <div className="" ref={i === products?.length - 1 ? lastElementRef : null}>
              <DiscountProduct key={k.productId} data={k} />
            </div>
            ) : (
              <div className="" ref={i === products?.length - 1 ? lastElementRef : null}>
                <Product key={k.productId} data={k} />
              </div>
            )
          )
        ) : (
          <Shimmer />
        )}
      </div>
      {
        loading && offset > 0 && <LoadingLine/>
      }
      <div className="h-[20px] w-full"></div>
    </>
  )
}
