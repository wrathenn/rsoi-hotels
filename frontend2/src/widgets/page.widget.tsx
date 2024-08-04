import React from 'react'
import { useOutlet } from 'react-router-dom'
import { PageFooter } from 'widgets/page-footer.widget.tsx'
import { PageHeader } from 'widgets/page-header.widget.tsx'

export const Page: React.FC<{}> = () => {
  const outlet = useOutlet();
  
  return (
    <div className="w-full h-screen grid grid-rows-[auto_1fr_auto] overflow-y-hidden bg-[#f7f7f9]">
      <PageHeader/>
      <section className="overflow-y-auto px-20 py-5">
        {outlet}
      </section>
      <PageFooter/>
    </div>
  )
}
