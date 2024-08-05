#!/usr/bin/env sh
set -eu

envsubst '${BACKEND_API_ADDR}' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf

exec "$@"
