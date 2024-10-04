import React from "react";
import { FaStar } from "react-icons/fa";
import { Link } from "react-router-dom";

const Product = ({ data }) => {
  return (
    <Link to={"/product/" + data.productId}>
      <div className="w-[300px] h-[450px] rounded-lg border border-1 border-zinc-600 text-gray-200 bg-zinc-700 hover:bg-zinc-900">
        <img
          src={data?.image}
          className="w-[300px] h-[250px] rounded-t-lg bg-gray-500"
          alt={data?.productName}
        />
        <div className="flex flex-col p-2">
          <div className="font-bold flex justify-center m-2">{data.brand}</div>
          <div className="text-lg flex justify-center">
            {data?.productName?.length > 22
              ? data.productName.slice(0, 22) + "..."
              : data.productName}
          </div>
          <div className="text-md flex justify-center">
            {data?.productDescription?.length > 45
              ? data?.productDescription?.slice(0, 45) + "..."
              : data?.productDescription}
          </div>
          <div className="flex justify-center items-end gap-2">
            <div className="flex  text-xl font-semibold">{data.price} ₹</div>
            <div className="text-gray-500 text-sm line-through">
              {Math.floor(data?.price + (data?.discount * data?.price) / 100)} ₹
            </div>
            <div
              className={
                data?.rating >= 4
                  ? "bg-green-500 text-white font-bold text-lg p-1 flex items-center"
                  : "bg-red-500 text-white font-bold text-lg p-1 flex items-center"
              }
            >
              <FaStar></FaStar>
              {data?.rating}
            </div>
          </div>
        </div>
      </div>
    </Link>
  );
};
export const discountedLabel = (Component) => {
  return (props) => {
    return (
      <div>
        <div className="absolute px-2 py-1 bg-yellow-500 rounded-lg text-white ml-[5px] mt-[5px] ">
          Best Deal
        </div>
        <Component {...props}></Component>
      </div>
    );
  };
};
export default Product;
