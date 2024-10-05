import { FaStar } from "react-icons/fa";

export default function ReviewBody({
  userName,
  comment,
  rating,
  profilePhoto,
}) {
  return (
    <div className="text-white w-[80%] my-3 py-2 bg-slate-600 px-5">
      <div className="flex">
        <img src={profilePhoto} className="w-10 h-10 my-auto" alt="" />
        &nbsp;&nbsp;
        <div className="">
          <div className="font-bold">{userName}</div>
          <div className="flex">
            <div
              className={
                rating >= 4
                  ? "bg-green-500 text-white font-bold w-14 text-lg p-1 flex items-center"
                  : "bg-red-500 text-white font-bold w-14  text-lg p-1 flex items-center"
              }
            >
              <FaStar></FaStar>
              &nbsp;
              {rating}
            </div>
            &nbsp;&nbsp;
            <div className="">{comment}</div>
          </div>
        </div>
      </div>
    </div>
  );
}
