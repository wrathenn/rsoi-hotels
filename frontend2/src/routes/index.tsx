import { createBrowserRouter, Navigate } from 'react-router-dom'
import { Page } from 'widgets/page.widget.tsx'
import { HotelsPage } from "views/hotels/hotels.page.tsx";
export const router = createBrowserRouter([
  {
    path: '/',
    Component: Page,
    children: [
      {
        index: true,
        element: <Navigate to="/hotels" />,
      },
      {
        path: "/hotels",
        Component: HotelsPage,
      },
      // {
      //   path: "/reservations",
      //   Component: TicketsPage
      // },
      // {
      //   path: "/profile",
      //   Component: BonusPage
      // },
    ]
  }
])
