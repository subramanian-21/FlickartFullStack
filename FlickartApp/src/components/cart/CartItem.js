import React from 'react'
import { FaStar } from 'react-icons/fa'
import { IoIosRemoveCircle } from 'react-icons/io'
import { Link } from 'react-router-dom'

export default function CartItem({product}) {
  return (
    <div
    className="h-[200px]  mx-auto my-2  border border-1 text-gray-300  border-zinc-700 shadow-md rounded-lg w-full flex items-center p-3 justify-between"
  >
    <Link to={"/product/" + product?.productId} className="flex">
      <div className="w-[150px] h-[150px]">
        <img
          className="w-[150px] h-[150px]"
          src={product?.image}
          alt=""
        />
      </div>
      <div className="m-1 md:m-4">
        <div className="text-sm md:text-lg font-bold">
          {product?.productName}
        </div>
        <div className="text-xs">{product?.brand}</div>
        <div className="flex items-end gap-2">
          <pre className="flex text-xl font-semibold">
            {product?.price}₹
          </pre>
          <div className="text-gray-500 text-sm line-through">
            {Math.floor(
              product?.price +
                (product?.discount * product?.price) / 100
            )}
            ₹
          </div>
          {product?.rating > 0 ? (
            <div
              className={
                product?.rating >= 4
                  ? "bg-green-500 text-white font-bold text-lg p-1 flex items-center gap-2 w-[100px] justify-center m-2"
                  : "bg-red-500 text-white font-bold text-lg p-1 flex items-center gap-2 w-[100px] justify-center m-2"
              }
            >
              <FaStar></FaStar>
              {product?.rating}
            </div>
          ) : (
            <div className="text-red-600 my-3">
              (No Ratings present)
            </div>
          )}
        </div>
      </div>
    </Link>

    <div
      // onClick={() => removeCartElement(k)}
      className="text-red-500 text-2xl"
    >
      <IoIosRemoveCircle />
    </div>
  </div>
  )
}
