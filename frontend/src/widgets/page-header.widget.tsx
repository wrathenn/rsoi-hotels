import { observer } from 'mobx-react-lite'
import React from 'react'
import { Link, useLocation } from 'react-router-dom'

export const PageHeader: React.FC = observer(() => {
    const location = useLocation()
    return (
        <header className="flex items-center justify-between gap-40 px-20 py-4 border-b bg-[#e39254] text-white">
            <Link to="/">
                <div className="text-3xl font-bold">Hotels rsoi</div>
            </Link>
            <div className="flex items-center grow justify-end gap-20">
                <Link to="/hotels">
                    <div className={`text-center hover:text-amber-800
                    ${location.pathname === "/hotels" ? "font-bold" : ""}`}>Забронировать отель</div>
                </Link>
                <Link to="/reservations">
                    <div className={`text-center hover:text-amber-800
                    ${location.pathname === "/reservations" ? "font-bold" : ""}`}>Мои бронирования</div>
                </Link>
                <Link to="/profile">
                    <div className={`text-center hover:text-amber-800
                    ${location.pathname === "/profile" ? "font-bold" : ""}`}>Бонусная программа</div>
                </Link>
            </div>
        </header>
    )
})
