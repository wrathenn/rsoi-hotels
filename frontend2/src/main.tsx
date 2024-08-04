import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import 'react-tooltip/dist/react-tooltip.css'
import 'temporal-polyfill/global'
import { cfg, keycloak } from "keycloak/kcloak.ts";

keycloak.init(cfg.init).then(() => {
    ReactDOM.createRoot(document.getElementById('root')!).render(
        <React.StrictMode>
            <App />
        </React.StrictMode>,
    )
})
