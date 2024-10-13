import React, { useState } from 'react'
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import postApi from '../utils/api/postApi';

export default function Signup() {
    const [showPropicInput, setShowPropicInput] = useState(false);

    const [inputData, setInputData] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setInputData({ ...inputData, [name]: value });
    };
    const navigate = useNavigate();
    const signup = async () => {
        try {
            const response = await postApi("/user/signup", inputData);
            setInputData({});
            toast.success("Account created successfully");
            navigate("/");

        } catch (error) {
            toast.error(error.response.data.response);
        }
    }
  return (
   <>
    <div className="w-full min-h-[100vh] bg-zinc-800 flex justify-center items-center text-white">
            <div className="w-[400px]  bg-zinc-900 flex justify-center gap-3 flex-col items-center">
                <header className="text-2xl font-bold p-4 w-full bg-blue-500">Flickart</header>
                <div className="flex flex-col   w-[80%]" >
                <label htmlFor="email" className="font-bold">Email</label>
                <input onChange={handleChange} value={inputData?.email} name='email' type="text" id="email" className="bg-gray-700  h-[50px] px-2 outline-none" placeholder="Enter email"/> 
                </div>
                <div className="flex flex-col   w-[80%]" >
                <label htmlFor="password" className="font-bold">Password</label>
                <input onChange={handleChange} value={inputData?.password} name='password'  type="text" id="password" className="bg-gray-700  h-[50px] px-2 outline-none" placeholder="Enter Password"/> 
                </div>

                <div className="flex flex-col   w-[80%]" >
                <label htmlFor="c-password" className="font-bold">Confirm password</label>
                <input onChange={handleChange} value={inputData?.Cpassword} name='Cpassword'  type="text" id="c-password" className="bg-gray-700  h-[50px] px-2 outline-none" placeholder="Confirm password"/> 
                </div>

                <div className="flex flex-col   w-[80%]" >
                <label htmlFor="c-password" className="font-bold">username</label>
                <input onChange={handleChange} value={inputData?.userName} name='userName'  type="text" id="c-password" className="bg-gray-700  h-[50px] px-2 outline-none" placeholder="Enter username"/> 
                </div>
                <div className="w-[80%]">
                    <input onChange={(e)=>setShowPropicInput(e.target.checked)} type="checkbox" name="" id="pro-pic" />
                    <label htmlFor="pro-pic" className='font-bold'>&nbsp;Add profile photo</label>
                   {
                    showPropicInput &&
                    <input onChange={handleChange} value={inputData?.profilePhoto} name='profilePhoto'  type="text" className="bg-gray-700 w-full  h-[50px] px-2 outline-none" placeholder="Enter Profile photo link"/> 
                   } 

                </div>

                <div className="">Already have an account ? <a href="/#/login">Login</a></div>
                <button onClick={signup} className="bg-blue-500 w-[80%] h-[50px] mb-[40px] font-bold">Signup</button>
            </div>
        </div>
   </>
  )
}
