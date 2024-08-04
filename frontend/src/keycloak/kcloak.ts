import Keycloak, { KeycloakConfig, KeycloakInitOptions } from "keycloak-js";

let keycloakUrl = new URL(location.origin);
keycloakUrl.pathname = "/auth/";

const env = import.meta.env.VITE_KEYCLOAK_ENDPOINT;
if (env) {
    keycloakUrl = new URL(`http://${env}/auth/`);
}

export const cfg: { create: KeycloakConfig; init: KeycloakInitOptions } = {
    create: {
        url: keycloakUrl.toString(),
        clientId: "frontend-client",
        realm: "rsoi-hotels",
    },
    init: {
        onLoad: "login-required",
        scope: "openid"
    }
} as const;

export const keycloak = new Keycloak(cfg.create);

export async function getKcloakToken() {
    const secondsBeforeExpiration = 30;
    await keycloak.updateToken(secondsBeforeExpiration);

    return keycloak.token;
}