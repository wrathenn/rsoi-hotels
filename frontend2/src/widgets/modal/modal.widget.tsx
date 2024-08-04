import React from 'react'
import CloseSvg from 'assets/close.svg?react'

export type ModalProps = {
  isModalShowed: boolean
  hideModal: (...args: any[]) => void
}

export const Modal: React.FC<React.PropsWithChildren<ModalProps>> = x => {
  return (
    x.isModalShowed ? (
      <div className="fixed top-0 left-0 z-50 backdrop-brightness-75 w-full h-full flex items-center justify-center"
           onClick={x.hideModal}>
        <div className="flex flex-col items-center bg-[#2f2a38] text-white w-[400px] px-2 pb-5 pt-2 rounded-xl" onClick={e => e.stopPropagation()}>
          <CloseSvg className="self-end cursor-pointer hover:text-blue-200" onClick={x.hideModal}/>
          {x.children}
        </div>
      </div>
    ) : <></>
  )
}