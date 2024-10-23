import React, { useState } from "react";
import { FaStar } from "react-icons/fa";
export default function ReviewStars({starCount, setStarCount}) {
   const handleStars = (star) => {
        setStarCount( star);
   }
  return (
    <div  className="flex gap-3">
      <div className="" >
        <FaStar onClick={()=>handleStars(1)} className={starCount >= 1? "text-yellow-500 text-xl":"text-white text-xl"}/>
      </div>{" "}
      <div className=""  data-index="1">
        <FaStar onClick={()=>handleStars(2)} className={starCount >= 2? "text-yellow-500 text-xl":"text-white text-xl"}/>
      </div>{" "}
      <div className=""  data-index="1">
        <FaStar onClick={()=>handleStars(3)} className={starCount >= 3? "text-yellow-500 text-xl":"text-white text-xl"}/>
      </div>{" "}
      <div className=""  data-index="1">
        <FaStar onClick={()=>handleStars(4)} className={starCount >= 4? "text-yellow-500 text-xl":"text-white text-xl"}/>
      </div>
      <div className=""  data-index="1">
        <FaStar onClick={()=>handleStars(5)} className={starCount >= 5? "text-yellow-500 text-xl":"text-white text-xl"}/>
      </div>
    </div>
  );
}
