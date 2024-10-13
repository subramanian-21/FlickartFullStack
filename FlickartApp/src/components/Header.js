import React, { useCallback, useContext, useEffect, useState } from "react";
import { FiSearch } from "react-icons/fi";
import { FaShoppingCart } from "react-icons/fa";
import { Link } from "react-router-dom";
import useGetSearchProducts from "../utils/customHooks/useGetSearchProducts";
import { DataContext } from "../utils/DataContext";
const Header = () => {
  const [searchText, setSearchText] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [apiSearchText, setApiSearchText] = useState("");
  const {user, isValidUser} = useContext(DataContext);
  const { loading, categories, products, error } = useGetSearchProducts(
    10,
    0,
    apiSearchText
  );
  useEffect(() => {
    if(isValidUser){
      setIsLoggedIn(true)
    }else {
      setIsLoggedIn(false);
    }
  }, [isValidUser]);
  function debounce(fn, time) {
    let timer;
    return (...args) => {
      clearTimeout(timer);
      timer = setTimeout(() => {
        fn(...args);
        console.log("reduced");
      }, time);
    };
  }

  const handleSearch = useCallback(
    debounce((value) => {
      setApiSearchText(value);
    }, 500), []
  );

  const onSearchInput = (e) => {
    const value = e.target.value;
    setSearchText(value);
    handleSearch(value); // Calls debounced search function
  };

  return (
    <>
      <div
        // onClick={() => setSearch("")}
        className="w-full bg-blue-500 h-[80px] flex justify-center items-center cursor-default"
      >
        <div className="w-full flex items-center">
          <Link
            to="/"
            className="text-white text-md  md:text-[30px] font-[600] m-3 italic"
          >
            Flickart
          </Link>
          <div className="flex w-full relative">
            <div className="w-[40px] h-[40px] bg-slate-200 rounded-l-lg flex justify-center items-center">
              <FiSearch className="text-[20px] text-gray-600" />
            </div>

            <input
              onInput={onSearchInput}
              type="text"
              className="w-full px-2 bg-slate-200 rounded-r-lg outline-none text-lg"
              placeholder="Search For Products, Categories and more..."
            />
            {searchText ? (
              <div
                className={
                  categories.length + products.length <= 5
                    ? "z-10 absolute w-full top-[40px]  "
                    : "z-10 absolute w-full top-[40px] h-[255px] overflow-y-scroll"
                }
              >
                <div className="h-[250px] ">
                  {categories.map((k) => (
                    <a
                      href={"/category/" + k.categoryName}
                      key={k}
                      className="bg-slate-300 text-black h-[50px] px-4 flex  items-center border border-b-1"
                    >
                      <img
                        src={k?.categoryImage}
                        className="md:w-[40px] md:h-[40px] w-[20px] h-[20px] mr-3"
                        alt=""
                      />
                      <div className="">
                        {k.categoryName}
                        <div className="text-xs">Categories</div>
                      </div>
                    </a>
                  ))}
                  {products.map((k) => (
                    <Link
                      to={"/product/" + k.productId}
                      key={k.title}
                      onClick={() => setSearchText("")}
                      className="bg-slate-300 text-xs text-black h-[70px] px-2 flex items-center border border-b-1 gap-2"
                    >
                      <img
                        src={k?.image}
                        className="md:w-[40px] md:h-[40px] w-[20px] h-[20px]"
                        alt=""
                      />
                      {k.productName}
                    </Link>
                  ))}
                </div>
              </div>
            ) : (
              <></>
            )}
          </div>
          <div
            // onClick={() => setContext(search)}
            className="md:px-4 md:py-2 md:text-md px-2 py-2 text-xs bg-green-500 rounded-lg m-2 text-white font-bold"
          >
            Search
          </div>
          <Link
            to={"/cart"}
            onClick={() => setSearchText("")}
            className="flex justify-center items-center  text-white cursor-pointer w-[100px] font-bold"
          >
            <FaShoppingCart className="text-white " />
            <div className="hidden md:block">&nbsp;Cart</div>
            <div className="">({user?.cart?.cartItems?.length})</div>
          </Link>

          {isLoggedIn ? (
            <div className="cursor-pointer w-[200px] text-white flex justify-center items-center mx-4 font-bold border-2 text-sm border-white px-2 py-1 rounded-lg">
              <img src={user?.profilePhoto} className="w-8 rounded-full" alt="" />
              {user?.userName}
            </div>
          ) : (
            <Link to="/login" className="cursor-pointer text-white font-bold border-2 border-white px-2 py-1 rounded-lg">
              Login
            </Link>
          )}
        </div>
      </div>
    </>
  );
};

export default Header;
