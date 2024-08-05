import React from "react";
import { StatSchema } from "api/schemas/statSchema.ts";

export const StatCard: React.FC<{
    stat: StatSchema.Dto
}> = x => {
    return (
    <div className="flex items-center justify-between bg-[#e39254] text-white w-full px-4 py-2 rounded-xl">
        <div className="w-[200px]">{x.stat.id}</div>
        <div>Время: {x.stat.ts?.toString()}</div>
        <div>Тип: {x.stat.data.type === "LOYALTY_RESTORED_UPDATE" ? "Восстановление" : "Повторный запрос"}</div>
        <div>Содержимое: {x.stat.data.loyaltyReservationCountOperation === "INCREMENT" ? "Инкремент лояльности" : "Декремент лояльности"}</div>
    </div>
)}
