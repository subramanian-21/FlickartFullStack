import { useState } from "react"
import toast from "react-hot-toast";
import { setAccessToken, setRefreshToken } from "../utils/util/localstorage";
import { useNavigate } from "react-router-dom";
import postApi from "../utils/api/postApi";

export default function Login() {
    const [inputData, setInputData]  = useState({});

    const handleInputChange = (e) =>{
        const {name, value} = e.target;
        setInputData((input)=>({...input, [name]:value}));
    }
    const navigate = useNavigate();
    const login =async () =>{
        if(inputData.email && inputData.password ) {
            try {
            const response = await postApi("/user/login",inputData);
            toast.success("Login successful");
            setAccessToken(response.response.accessToken);
            setRefreshToken(response.response.refreshToken);
            setInputData({})
            navigate("/");
            }catch(err) {
                console.log(err)
                toast.error(err.response.data.response);
            }
        }else {
            toast.error("Please fill all the fields");
        }
       
    }
  return (
    <>
        <div className="w-full min-h-[100vh] bg-zinc-800 flex justify-center items-center text-white">
            <div className="w-[400px]  bg-zinc-900 flex justify-center gap-10 flex-col items-center">
                <header className="text-2xl font-bold p-4 w-full bg-blue-500">Flickart</header>
                <div className="flex flex-col   w-[80%]" >
                <label htmlFor="email" className="font-bold">Email</label>
                <input onInput={handleInputChange} name="email" type="text" id="email" className="bg-gray-700  h-[50px] px-2 outline-none" placeholder="Enter email"/> 
                </div>
                <div className="flex flex-col   w-[80%]" >
                <label htmlFor="password" className="font-bold">Password</label>
                <input onInput={handleInputChange} name="password" type="text" id="password" className="bg-gray-700  h-[50px] px-2 outline-none" placeholder="Enter Password"/> 
                </div>
                <div className="">Don't have an account ? <a href="/#/signup">Signup</a></div>
                <button onClick={login} className="bg-blue-500 w-[80%] h-[50px] mb-[50px] font-bold">Login</button>
            </div>
        </div>
    </>
  )
}
