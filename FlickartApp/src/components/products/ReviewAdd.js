import React, { useContext, useState } from "react";
import ReviewStars from "./ReviewStars";
import toast from "react-hot-toast";
import { addProductReviewApi } from "../../utils/api/addProductReview";
import { DataContext } from "../../utils/DataContext";

export default function ReviewAdd({productId, fetchProduct}) {
    const [starCount, setStarCount] = useState(0);
    const [comment, setComment] = useState("");
    const {userId} = useContext(DataContext);
    const addReview = async () => {
        if(starCount === 0 ){
            toast.error("Add rating");
            return;
        }
        if(comment === ''){
            toast.error("Add Comment");
            return;
        }
        const data = {
            rating: starCount,
            comment: comment,
            userId: userId,
            productId: productId
        }
        try {
            const response = await addProductReviewApi(data);
            toast.success(response.response);
            fetchProduct();
        } catch (error) {
            toast.error(error.response.data.response);
        }
    }
  return (
    <div className="flex justify-center flex-col items-center">

      <div className="bg-gray-800  md:w-[60%] h-[200px]">
        <div className="font-bold text-white text-2xl my-5 mx-5">Add Review</div>
        <div className="my-5 mx-5">
        <ReviewStars starCount={starCount} setStarCount={setStarCount}/>
        </div>
        <div className="flex gap-2">
        <textarea onInput={(e)=>setComment(e.target.value)} name="email" type="text"  id="email" className="bg-gray-700 w-[80%] h-[50px] px-2 outline-none min-h-[40px] text-white" placeholder="Product review.."/> 
        <button onClick={addReview} className="text-white bg-zinc-900 px-6 max-h-[60px] min-h-[40px]">Submit</button>
        </div>
        
      </div>
    </div>
  );
}
