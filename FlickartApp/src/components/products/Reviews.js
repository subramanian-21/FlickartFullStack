import ReviewAdd from "./ReviewAdd";
import ReviewBody from "./ReviewBody";

export default function Reviews({ reviews }) {
  return (
    <>
    <div className="font-bold text-white text-2xl text-center w-[80%] mb-5">Product Reviews</div>
        <div className="flex flex-col justify-center items-center w-[80%] ">
          {reviews.length > 0 ? reviews?.map((k, i) => (
            <ReviewBody profilePhoto={k.userDetails?.profilePhoto} userName={k.userDetails?.userName} comment={k?.comment} rating={k?.rating}/>
          )) : <div className="text-red-500">No reviews present</div>}
        </div>
    </>
  )
}