import { useEffect } from 'react'
import { RouterProvider } from 'react-router-dom'
import { router } from 'routes/index.tsx'

export const App = () => {
  return (
    <RouterProvider router={router} />
  )
}

export default App
