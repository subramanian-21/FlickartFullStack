import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { FaStar } from "react-icons/fa";
import { IoIosRemoveCircle } from "react-icons/io";
import { removeFromCart } from "../utils/redux/cartSlice";
import { Link } from "react-router-dom";
const CartBody = () => {
  const [price, setPrice] = useState(0);
  const dispatch = useDispatch();
  const cartItems = useSelector((k) => k.cart.products);
  useEffect(() => {
    const priceAmt = cartItems.reduce((acc, c) => {
      return acc + c.price;
    }, 0);
    setPrice(priceAmt);
  }, [cartItems]);
  function removeCartElement(k) {
    dispatch(removeFromCart(k));
  }
  return (
    <div className="mx-auto cursor-default w-full md:w-4/5 lg:w-3/5 min-h-[85vh] bg-gray-50  relative flex justify-center mb-20">
      <div className="w-[95%] md:w-4/5 lg:w-3/5 ">
        <div className="">
          {cartItems.length > 0 ? (
            cartItems.map((k) => (
              <div key={k.id}   className="h-[200px]  mx-auto my-2  border border-1  border-gray-300 shadow-md rounded-lg w-full flex items-center p-3 justify-between">
                <Link to={"/product/"+k.id} className="flex">
                  <div className="w-[150px] h-[150px]">
                    <img
                      className="w-[150px] h-[150px]"
                      src={k.thumbnail}
                      alt=""
                    />
                    
                  </div>
                  <div className="m-1 md:m-4">
                    <div className="text-sm md:text-lg font-bold">{k.title}</div>
                    <div className="text-xs">{k.brand}</div>
                    <div className="flex items-end gap-2">
                      <pre className="flex text-xl font-semibold">
                        {k.price}$
                      </pre>
                      <div className="text-gray-500 text-sm line-through">
                        {Math.floor(
                          k.price + (k.discountPercentage * k.price) / 100
                        )}$
                      </div>
                      <div
                        className={
                          k.rating >= 4
                            ? "bg-green-500 text-white font-bold text-lg p-1 flex items-center"
                            : "bg-red-500 text-white font-bold text-lg p-1 flex items-center"
                        }
                      >
                        <FaStar></FaStar>
                        {k.rating}
                      </div>
                    </div>
                  </div>
                </Link>

                <div
                  onClick={() => removeCartElement(k)}
                  className="text-red-500 text-2xl"
                >
                  <IoIosRemoveCircle />
                </div>
              </div>
            ))
          ) : (
            <div className="font-bold text-2xl mx-auto flex justify-center items-center my-[50px]">
              Cart is Empty
            </div>
          )}
        </div>
      </div>
      <div className="fixed w-full h-[80px] bg-gray-200 bottom-0 flex justify-center items-center">
        <div className="w-full md:w-4/5 lg:w-3/5 flex justify-center">
          {price > 0 ? (
            <>
              <div className="w-full md:w-4/5 lg:w-3/5 flex justify-end text-lg items-center">
                Total : <div className="font-bold text-xl"> {price}$</div>
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
