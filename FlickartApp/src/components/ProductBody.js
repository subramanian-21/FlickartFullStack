import React, { useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { FaStar } from "react-icons/fa";
import { useState } from "react";
import { getProduct } from "../utils/api/getProducts";
import Reviews from "./products/Reviews";
import { addToCartApi } from "../utils/api/addToCart";
import { getCartId } from "../utils/util/localstorage";
import toast from "react-hot-toast";
import { DataContext } from "../utils/DataContext";
const ProductBody = () => {
  const [image,setImage] = useState("")
  const [product,setProduct] = useState(null)
  const [isAdded, setAdded] = useState(false);
   const {productId} = useParams();
  const {cartId, fetchUserData} = useContext(DataContext);
  const navigate = useNavigate();
  async function fetchData() {
    try {
      const data = await getProduct(productId);
      console.log(data.response);
      setProduct(data.response);
    } catch (error) {
      console.log(error);
      toast.error(error.response.data.response);
      navigate("/");
    }
   
  }
 
  useEffect(()=>{
    fetchData()
  },[productId])

  async function handleAddToCart() {
    const response = await addToCartApi(product.productId, cartId);
    toast.success(response.response);
    await fetchUserData();
    setAdded(true);
  }
  if (!product) {
    return <></>;
  }
  function changeImage(d){
    setImage(d)
  }
  
  return (
    <div>
      <div className="w-full flex flex-col md:flex-row text-white">
        <div className="md:w-1/3 gap-2 p-2 flex">
          <div className="">
            {product.images?.length > 0  ?product.images.map((k,i) => (
              <img
              onMouseEnter={()=>changeImage(k)}
              key={i}
                src={k}
                className="w-[50px] h-[50px] rounded-lg border border-1 border-black m-2"
                alt=""
              />
            )):""}
          </div>
          <div className=" m-2">
            <img src={product.image} alt="" />
          </div>
        </div>
        <div className="md:w-2/3 p-5">
          <div className="text-2xl">{product.productName}</div>
          <div className="font-bold text-xl">{product.brand}</div>
          <div className="texl-xl">{product.productDescription}</div>
          {
            product.rating > 0 ? <div
            className={
              product.rating >= 4
                ? "bg-green-500 text-white font-bold text-lg p-1 flex items-center gap-2 w-[100px] justify-center m-2"
                : "bg-red-500 text-white font-bold text-lg p-1 flex items-center gap-2 w-[100px] justify-center m-2"
            }
          >
            <FaStar></FaStar>
            {product.rating}
          </div> : <div className="text-red-600 my-3">(No Ratings present)</div> 
          }
          
          <div className="h-[1px] border border-1 border-gray-700"></div>

          <div className="text-gray-300 text-sm line-through">
            {Math.floor(
              product.price + (product.discount * product.price) / 100
            )}{" "}
            ₹
          </div>
          <div className="text-2xl gap-4 flex items-center">
            <div className="text-4xl text-red-400">
              -{product.discount}%
            </div>
            {product.price} ₹{" "}
          </div>

          <div className="">(Inclusive All taxes)</div>
          <div className="h-[1px] border border-1 border-gray-700"></div>
          <div className={product.stockCount>10?"text-green-500 my-4":"text-red-500 my-4"}>Only {product.stockCount} stocks Left</div>
          <button
            onClick={handleAddToCart}
            disabled={isAdded}
            className={
              isAdded
                ? "h-[70px] w-[200px] justify-center flex items-center bg-orange-500 m-2 text-white text-xl cursor-pointer"
                : "h-[70px] w-[200px] justify-center flex items-center bg-red-500 m-2 text-white text-xl cursor-pointer"
            }
          >
            {isAdded ? "Added to Cart"  : "Add To Cart"}
          </button>
        </div>
      </div>
            <Reviews reviews={product.reviews}/>
    </div>
  );
};

export default ProductBody;
