import { Outlet } from "react-router-dom";
import Header from "../components/Header";
import React from 'react'

const Home =  () => {

  return (
    
    <div className="bg-zinc-900 min-h-[100vh]">
    <Header></Header>
    <div>
    <Outlet />
    </div>
    
    </div>
  
  )
}

export default Home
