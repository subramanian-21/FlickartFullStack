import "./App.css";
import { lazy, Suspense } from "react";
import {  RouterProvider,createHashRouter } from "react-router-dom";
import Home from "./pages/Home";
import HomeBody from "./components/HomeBody";
import { DataProvider } from "./utils/DataContext";
import { Provider } from "react-redux";
import store from "./utils/redux/store";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import { Toaster } from "react-hot-toast";


function App() {
  const ProductBody = lazy(() => import("./components/ProductBody"));
  const CartBody = lazy(()=>import("./components/CartBody"))
  const appRouter = createHashRouter([
    {
      path: "/login",
      element: <Login />,
     
    },
    {
      path: "/signup",
      element: <Signup />,
     
    },
    {
      path: "/",
      element: <Home />,
      children: [
        {
          path: "",
          element: <HomeBody />,
        },
        {
          path: "home",
          element: <HomeBody />,
        },
        {
          path: "cart",
          element: <Suspense fallback={<h1>Loading...</h1>}>
          <CartBody />
        </Suspense>,
        },
        {
          path: "product/:id",
          element: (
            <Suspense fallback={<h1>Loading...</h1>}>
              <ProductBody />
            </Suspense>
          ),
        },
       
      ],
    },
  ]);
  return (
    <Provider store={store}>
      <Toaster />
      <DataProvider>
        <RouterProvider router={appRouter} />
      </DataProvider>
    </Provider>
  );
}

export default App;
