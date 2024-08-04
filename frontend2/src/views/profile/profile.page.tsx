import { observer } from 'mobx-react-lite'
import React, { useEffect } from 'react'
import { ProfileVm } from "views/profile/profile.vm.ts";

export const ProfilePage: React.FC = observer(() => {
  useEffect(() => {
    void ProfileVm.loadData()
  }, [])
  
  
  return (
    <div className="flex flex-col gap-5">
      <h1>Бонусная программа</h1>
      {
        ProfileVm.privilegeInfo && (
          <div className="flex flex-col gap-5">
            <div className="flex items-center gap-1">
              <div>Уровень привилегий:</div>
              <div>{ProfileVm.privilegeInfo.status}</div>
            </div>
            <div className="flex items-center gap-1">
              <div>Скидка:</div>
              <div>{ProfileVm.privilegeInfo.discount} %</div>
            </div>
            <div className="flex items-center gap-1">
              <div>Количество бронирований:</div>
              <div>{ProfileVm.privilegeInfo.reservationCount}</div>
            </div>
          </div>
        )
      }
    </div>
  )
})
