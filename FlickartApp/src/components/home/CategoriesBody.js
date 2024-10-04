export default function CategoriesBody({categories}) {
  return (
    <>
     <div className=" h-[250px] bg-zinc-800 m-3 flex justify-center items-center">
        <div className="w-[80%] h-full flex items-center overflow-x-auto whitespace-nowrap">
          {categories?.map((k, i) => (
            <a
            href={"/category/" + k.categoryName}
              key={k.categoryName}
              className="m-2 relative flex-shrink-0 rounded-lg flex flex-col items-center cursor-pointer w-[300px] h-[200px]"
            >
              <div className="absolute w-full rounded-lg h-full transparent-white flex justify-center items-center text-xl font-bold text-black">
                {k?.category}
              </div>
              <img
                className="rounded-lg w-full h-full"
                src={k?.categoryImage}
                alt=""
              />
            </a>
          ))}
        </div>
      </div>
    </>
  )
}
