#!/bin/bash

set -o errexit -o pipefail -o noclobber

until pg_isready --quiet --timeout=1; do sleep 1; done

export PGDATABASE=postgres

psql --set=ON_ERROR_STOP=1 --file=/hotel-db/init/00_users.sql
psql --set=ON_ERROR_STOP=1 --file=/hotel-db/init/01_reservation.sql
psql --set=ON_ERROR_STOP=1 --file=/hotel-db/init/02_payment.sql
psql --set=ON_ERROR_STOP=1 --file=/hotel-db/init/03_loyalty.sql
psql --set=ON_ERROR_STOP=1 --file=/hotel-db/init/04_stats.sql
