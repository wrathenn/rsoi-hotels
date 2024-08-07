import { createBrowserRouter, Navigate } from 'react-router-dom'
import { Page } from 'widgets/page.widget.tsx'
import { HotelsPage } from "views/hotels/hotels.page.tsx";
import { ReservationsPage } from "views/reservations/reservations.page.tsx";
import { ProfilePage } from "views/profile/profile.page.tsx";
import { StatsPage } from "views/stats/stats.page.tsx";
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
      {
        path: "/reservations",
        Component: ReservationsPage,
      },
      {
        path: "/profile",
        Component: ProfilePage,
      },
      {
        path: "/stats",
        Component: StatsPage,
      },
    ]
  }
])
