import React, { useContext } from "react";
import "../styles/global.css";
import Product, { discountedLabel } from "./Product";
import DataContext from "../utils/DataContext";
import Shimmer from "./Shimmer";
const HomeBody = () => {
  const {
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
  } = useContext(DataContext);

  const DiscountProduct = discountedLabel(Product);
  console.log(products);
  console.log(categories);
  return loading ?<Shimmer/> : (
    <div>
      <div className=" h-[250px] bg-zinc-800 m-3 flex justify-center items-center">
        <div className="w-[80%] h-full flex items-center overflow-x-auto whitespace-nowrap">
          {categories?.map((k, i) => (
            <div
              key={i}
              className="m-2 relative flex-shrink-0 rounded-lg flex flex-col items-center cursor-pointer w-[300px] h-[200px]"
            >
              <div className="absolute w-full rounded-lg h-full transparent-white flex justify-center items-center text-xl font-bold text-black">
                {k.category}
              </div>
              <img
                className="rounded-lg w-full h-full"
                src={k.categoryImage}
                alt=""
              />
            </div>
          ))}
        </div>
      </div>
      <div className=" h-500px bg-white m-3 flex gap-2 flex-wrap justify-center p-3">
        {/* {modifyingProducts.length > 0 ? (
          modifyingProducts.map((k) =>
            k.discountPercentage > 15 ? (
              <DiscountProduct key={k.id} data={k} />
            ) : (
              <Product key={k.id} data={k} />
            )
          )
        ) : (
          <Shimmer />
        )} */}
      </div>
      <div className="flex justify-center">
        {/* {modifyingProducts.length === 25 ? (
          <div className="flex bg-white">
            {pageArray.map((k) => (
              <div
                key={k}
                onClick={() => pagination(k)}
                className={
                  filterCount === k
                    ? "w-[40px] bg-slate-500 h-[40px] flex justify-center items-center cursor-pointer outline outline-1"
                    : "w-[40px] h-[40px] flex justify-center items-center cursor-pointer outline outline-1"
                }
              >
                {k}
              </div>
            ))}
          </div>
        ) : (
          <></>
        )} */}
      </div>
      <div className="h-[20px] w-full"></div>
    </div>
  );
};
export default HomeBody;
