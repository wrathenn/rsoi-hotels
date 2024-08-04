import { observer } from 'mobx-react-lite'
import React, { useEffect } from 'react'
import { Navigate } from 'react-router-dom'
import { UserStore } from 'stores/user.store.ts'
import { BonusVm } from 'views/bonus/bonus.vm.ts'

export const ProfilePage: React.FC<{}> = observer(() => {
  useEffect(() => {
    void BonusVm.loadData()
  }, [])
  
  
  return (
    <div className="flex flex-col gap-5">
      { !UserStore.isAuth && <Navigate to={"/"}/> }
      <h1>Бонусная программа</h1>
      {
        BonusVm.privilegeInfo && (
          <div className="flex flex-col gap-5">
            <div className="flex items-center gap-1">
              <div>Уровень привилегий:</div>
              <div>{BonusVm.privilegeInfo.status}</div>
            </div>
            <div className="flex items-center gap-1">
              <div>Баланс:</div>
              <div>{BonusVm.privilegeInfo.balance} бонусов</div>
            </div>
            <div className="flex flex-col gap-2">
              <h2>История изменений</h2>
              { BonusVm.privilegeInfo.history.length === 0 && <h3>История пуста</h3> }
              <div className="flex flex-col gap-2 indent-4">
                {
                  BonusVm.privilegeInfo.history.map((r, i) => (
                    <div className="flex items-center gap-2" key={i}>
                      <div className="w-[60px]">{r.operationType === "FILL_IN_BALANCE" ? "+" : "-"}{r.balanceDiff}</div>
                      <div>Операция по билету {r.ticketUid.split("-")[0]}</div>
                    </div>
                  ))
                }
              </div>
            </div>
          
          </div>
        )
      }
    </div>
  )
})
