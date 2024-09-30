import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { FaStar } from "react-icons/fa";
import { useState } from "react";
import { addToCart } from "../utils/redux/cartSlice";
import { useDispatch, useSelector } from "react-redux";
const ProductBody = () => {
  const [image,setImage] = useState("")
  const cartItems = useSelector(c=>c.cart.products)
  const [product,setProduct] = useState(null)
  const dispatch = useDispatch()
  const [cart, setCart] = useState("Add to Cart");
  const params = useParams();
  const proId = params.id;

  async function fetchData() {
    
    const res = await fetch("https://dummyjson.com/products/" + proId);
    const json = await res.json();
    
    const present = cartItems.find(k=>k.id === json.id)
    if(present){
      setCart("Added to Cart")
    }else{
      setCart("Add to Cart")
    }
    setImage(json.thumbnail)
    setProduct(json);
  }
  const handleAddCart=(payload)=>{
    setCart('Added to Cart')
    dispatch(addToCart(payload))
  }
  useEffect(()=>{
    fetchData()
  },[proId,cartItems])

  if (!product) {
    return <></>;
  }
  function changeImage(d){
    setImage(d)
  }
 
  return (
    <div>
      <div className="w-full min-h-[80vh] flex flex-col md:flex-row">
        <div className="md:w-1/3 gap-2 p-2 flex">
          <div className="">
            {product.images?product.images.map((k,i) => (
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
            <img src={image} alt="" />
          </div>
        </div>
        <div className="md:w-2/3 p-5">
          <div className="text-2xl">{product.title}</div>
          <div className="font-bold text-xl">{product.brand}</div>
          <div className="texl-xl">{product.description}</div>
          <div
            className={
              product.rating >= 4
                ? "bg-green-500 text-white font-bold text-lg p-1 flex items-center gap-2 w-[100px] justify-center m-2"
                : "bg-red-500 text-white font-bold text-lg p-1 flex items-center gap-2 w-[100px] justify-center m-2"
            }
          >
            <FaStar></FaStar>
            {product.rating}
          </div>
          <div className="h-[1px] border border-1 border-gray-700"></div>

          <div className="text-gray-500 text-sm line-through">
            {Math.floor(
              product.price + (product.discountPercentage * product.price) / 100
            )}{" "}
            $
          </div>
          <div className="text-2xl gap-4 flex items-center">
            <div className="text-4xl text-red-400">
              -{product.discountPercentage}%
            </div>
            {product.price} ${" "}
          </div>

          <div className="">(Inclusive All taxes)</div>
          <div className="h-[1px] border border-1 border-gray-700"></div>
          <div className={product.stock>10?"text-green-500 my-4":"text-red-500 my-4"}>Only {product.stock} Left</div>
          <button
            onClick={() => handleAddCart(product)}
            disabled={cart !=="Add to Cart"}
            className={
              cart === "Add to Cart"
            
                ? "h-[70px] w-[200px] justify-center flex items-center bg-orange-500 m-2 text-white text-xl cursor-pointer"
                : "h-[70px] w-[200px] justify-center flex items-center bg-red-500 m-2 text-white text-xl cursor-pointer"
            }
          >
            {cart}
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductBody;
