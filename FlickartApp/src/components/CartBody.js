import React, { useContext, useEffect, useState } from "react";
import { FaStar } from "react-icons/fa";
import { IoIosRemoveCircle } from "react-icons/io";
import { Link } from "react-router-dom";
import { DataContext } from "../utils/DataContext";
import CartItem from "./cart/CartItem";
const CartBody = () => {
  const [price, setPrice] = useState(0);
  const { cartItems } = useContext(DataContext);
  useEffect(() => {
    const priceAmt = cartItems?.reduce((acc, c) => {
      return acc + c?.product?.price;
    }, 0);
    setPrice(priceAmt);
  }, [cartItems]);

  return (
    <div className="mx-auto cursor-default w-full md:w-4/5 lg:w-3/5 min-h-[85vh] bg-zinc-800  relative flex justify-center mb-20">
      <div className="w-[95%] md:w-4/5 lg:w-3/5 ">
        <div className="">
          {cartItems?.length > 0 ? (
            cartItems?.map((k) => (
             <CartItem key={k?.product?.productId} cartItem={k} />
            ))
          ) : (
            <div className="font-bold text-2xl text-white mx-auto flex justify-center items-center my-[50px]">
              Cart is Empty
            </div>
          )}
        </div>
      </div>
      <div className="fixed w-full text-white h-[80px] bg-black bottom-0 flex justify-center items-center">
        <div className="w-full md:w-4/5 lg:w-3/5 flex justify-center">
          {price > 0 ? (
            <>
              <div className="w-full md:w-4/5 lg:w-3/5 flex justify-end text-lg items-center">
                Total : <div className="font-bold text-xl"> {price} ₹</div>
              </div>
              <div className="px-4 py-2 shadow-md rounded-lg bg-green-500 mx-4">
                Buy Now
              </div>
            </>
          ) : (
            <></>
          )}
        </div>
      </div>
    </div>
  );
};

export default CartBody;
