import { createContext } from "react";
import { useState } from "react";
import useFetchData from "./customHooks/useFetchData";

const DataContext = createContext({})

export const DataProvider = ({children})=>{
    const [searchText,setSearchText] = useState("")
    const apiData = useFetchData("https://dummyjson.com/products?limit=100")
    const allDataCategory = apiData.map((k) => k.category);
    const dataCategory = allDataCategory.filter(
      (k, i) => allDataCategory.indexOf(k) === i
    );
    return(<DataContext.Provider value={{searchText,setSearchText,apiData,dataCategory}}>{children}</DataContext.Provider>)
}
export default DataContext