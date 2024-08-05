import { observer } from 'mobx-react-lite'
import React, { useEffect } from 'react'
import { StatsVm } from "views/stats/stats.vm.ts";
import { StatCard } from "views/stats/widgets/stat-card.widget.tsx";

export const StatsPage: React.FC = observer(() => {
  useEffect(() => {
    void StatsVm.loadData()
  }, [])
  
  
  return (
    <div className="flex flex-col gap-5">
      <h1>Логи статистики:</h1>
      {
        StatsVm.stats && (
          <div className="flex flex-col gap-5">
            {
              StatsVm.stats.map(stat => <StatCard stat={stat}/>)
            }
          </div>
        )
      }
    </div>
  )
})
