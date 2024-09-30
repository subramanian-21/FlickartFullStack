import React, { useContext, useEffect, useState } from "react";
import { FiSearch } from "react-icons/fi";
import { FaShoppingCart } from "react-icons/fa";
import DataContext from "../utils/DataContext";
import { Provider, useSelector } from "react-redux";
import { Link } from "react-router-dom";
const Header = () => {
  const cartLen = useSelector((c) => c.cart.products);
  const {  setSearchText, apiData, dataCategory } =
    useContext(DataContext);
  const [search, setSearch] = useState("");
  const [category, setCategory] = useState([]);
  const [filteredCategory, setFilteredCategory] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const setContext = (e) => {
    setSearch("");
    setSearchText(e);
  };
  useEffect(() => {
    setCategory(dataCategory);
  }, [dataCategory]);

  const filterApiData = (data) => {
    setSearch(data);
    const dC = category.filter((k) => k.includes(data.toLowerCase()));
    setFilteredCategory(dC);
    const dataTitle = apiData.filter((k) =>
      k.title.toLowerCase().includes(data.toLowerCase())
    );
    setFilteredData(dataTitle);
  };
  function debounce(fn, time) {
    let timer;
    return (...args)=>{
      clearTimeout(timer);
      timer = setTimeout(() => {
        fn(...args);
        console.log("reduced")
      }, time);
    }
    
  }

  const debouncedSearch = debounce(filterApiData,500)
  function enterToSearch(e) {
    if (e.keyCode === 13) {
      setSearch("");
      setSearchText(e.target.value);
    }
  }
  return (
    <>
      <div
        onClick={() => setSearch("")}
        className="w-full bg-blue-500 h-[80px] flex justify-center items-center cursor-default"
      >
        <div className="w-full md:w-[90%] flex items-center">
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
              onInput={(e) => debouncedSearch(e.target.value)}
              onKeyUp={(e) => enterToSearch(e)}
              type="text"
              className="w-full px-2 bg-slate-200 rounded-r-lg outline-none text-lg"
              placeholder="Search For Products, Categories and more..."
            />
            {search ? (
              <div
                className={
                  filteredCategory.length + filteredData.length <= 5
                    ? "z-10 absolute w-full top-[40px]  "
                    : "z-10 absolute w-full top-[40px] h-[255px] overflow-y-scroll"
                }
              >
                <div className="h-[250px] ">
                  {filteredCategory.map((k) => (
                    <Link
                      to={"/"}
                      key={k}
                      onClick={() => setContext(k)}
                      className="bg-slate-300 text-black h-[50px] px-4 flex flex-col justify-center border border-b-1"
                    >
                      {k}
                      <div className="text-xs">Categories</div>
                    </Link>
                  ))}
                  {filteredData.map((k) => (
                    <Link
                      to={"/product/" + k.id}
                      key={k.title}
                      onClick={() => setSearch("")}
                      className="bg-slate-300 text-xs text-black h-[70px] px-2 flex items-center border border-b-1 gap-2"
                    >
                      <img
                        src={k.thumbnail}
                        className="md:w-[40px] md:h-[40px] w-[20px] h-[20px]"
                        alt=""
                      />
                      {k.title}
                    </Link>
                  ))}
                </div>
              </div>
            ) : (
              <></>
            )}
          </div>
          <div
            onClick={() => setContext(search)}
            className="md:px-4 md:py-2 md:text-md px-2 py-2 text-xs bg-green-500 rounded-lg m-2 text-white font-bold"
          >
            Search
          </div>
          <Link
            to={"/cart"}
            onClick={() => setSearch("")}
            className="flex justify-center items-center  text-white cursor-pointer w-[100px] font-bold"
          >
            <FaShoppingCart className="text-white " />
            <div className="hidden md:block">&nbsp;Cart</div>
            <div className="">({cartLen.length})</div>
          </Link>
        </div>
      </div>
    </>
  );
};

export default Header;
