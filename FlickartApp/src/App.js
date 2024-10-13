import "./App.css";
import { lazy, Suspense } from "react";
import {  RouterProvider,createBrowserRouter} from "react-router-dom";
import Home from "./pages/Home";
import HomeBody from "./components/home/HomeBody";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import { Toaster } from "react-hot-toast";
import Category from "./pages/Category";
import Loading from "./components/Loading";
import { DataProvider } from "./utils/DataContext";

function App() {
  const ProductBody = lazy(() => import("./components/ProductBody"));
  const CartBody = lazy(() => import("./components/CartBody"));

  const appRouter = createBrowserRouter([
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
          element: (
            <Suspense fallback={<Loading />}>
              <HomeBody />
            </Suspense>
          ),
        },
        {
          path: "home",
          element: <HomeBody />,
        },
        {
          path: "category/:categoryId",
          element: <Category />,
        },
        {
          path: "product/:productId",
          element: (
            <Suspense fallback={<Loading />}>
              <ProductBody />
            </Suspense>
          ),
        },
        {
          path: "cart",
          element: (
            <Suspense fallback={<Loading />}>
              <CartBody />
            </Suspense>
          ),
        },
      ],
    },
  ]);

  return (
    <>
      <DataProvider>
        <Toaster />
        <RouterProvider router={appRouter} />
      </DataProvider>
    </>
  );
}

export default App;
