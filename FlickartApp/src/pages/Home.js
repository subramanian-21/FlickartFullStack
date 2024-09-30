import { Outlet } from "react-router-dom";
import Header from "../components/Header";
import { useState,useEffect } from "react";
import React from 'react'

const Home =  () => {

  return (
    
    <div className="bg-gray-100 min-h-[100vh]">
    <Header></Header>
    <div>
    <Outlet />
    </div>
    
    </div>
  
  )
}

export default Home
