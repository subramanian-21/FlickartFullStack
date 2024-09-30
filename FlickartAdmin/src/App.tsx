import { createBrowserRouter, RouterProvider } from "react-router-dom"

function App() {
  const router = createBrowserRouter([
    {
      path:'/',
      element:<>login</>
    }, 
    {
      path:'/login',
      element:<>login</>
    }
    , 
    {
      path:'/products',
      element:<>products</>,
      children:[
        {
          path:':id',
          element:<>id</>
        },
        {
          path:'add',
          element:<>abhjjbjdd</>
        },
        {
          path:'update',
          element:<>update</>
       }
      ]
    }
  ])

  return (
    <>
     <RouterProvider router={router} />
    </>
  )
}

export default App
