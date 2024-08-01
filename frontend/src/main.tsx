import "temporal-polyfill/global";
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import { cfg, keycloak } from "./kcloak.ts";

keycloak.init(cfg.init).then(() => {
    ReactDOM.createRoot(document.getElementById('root')!).render(
        <React.StrictMode>
            <App/>
        </React.StrictMode>,
    );
});
