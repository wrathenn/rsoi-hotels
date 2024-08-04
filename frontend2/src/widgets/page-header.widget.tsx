import { observer } from 'mobx-react-lite'
import React from 'react'
import { Link } from 'react-router-dom'
import { Tooltip } from 'react-tooltip'

export const PageHeader: React.FC = observer(() => {
  return (
    <header className="flex items-center justify-between gap-40 px-20 py-4 border-b bg-[#2f2a38] text-white">
      <Link to="/">
        <div className="text-3xl font-bold">Fly High</div>
      </Link>
      <div className="flex items-center grow">
        <Link to="/flights">
          <div className="font-semibold hover:text-blue-500">Купить билет</div>
        </Link>
      </div>
      <div>
        <>
          <div id="profile" className="cursor-pointer font-semibold">Личный кабинет</div>
          <Tooltip anchorSelect="#profile" place="bottom" clickable style={{background: "white", color: "black"}} opacity={1}>
            <div className="flex flex-col items-center gap-2 ">
              <Link to="/tickets" className="w-full">
                <div className="text-center hover:text-blue-500">Мои билеты</div>
              </Link>
              <Link to="/bonus" className="w-full">
                <div className="text-center hover:text-blue-500">Бонусная программа</div>
              </Link>
              {/*<div className="text-center hover:text-blue-500 cursor-pointer" onClick={() => UserStore.logout()}>*/}
              {/*  Выйти из аккаунта*/}
              {/*</div>*/}
            </div>
          </Tooltip>
        </>
      </div>
    </header>
  )
})
