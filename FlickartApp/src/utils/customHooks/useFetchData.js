import { useState, useEffect } from "react";
const useFetchData = (url) => {
  const [data, setData] = useState([]);
  async function fetchData() {
    const res = await fetch(url);
    const json = await res.json();
    setData(json);
  }
  useEffect(() => {
    fetchData();
  }, []);

  return data.products ? data.products : data;
};
export default useFetchData;
