export default function LoadingLine() {
    return (
      <div className='w-full h-[80px] flex justify-center items-center gap-3'>
       <div className="w-[20px] h-[20px] animate-ping bg-yellow-400"></div>
       <div className="w-[20px] h-[20px] animate-ping bg-red-500"></div>
       <div className="w-[20px] h-[20px] animate-ping bg-blue-500"></div>
      </div>
    )
  }