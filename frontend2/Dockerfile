FROM node:20-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install --production
COPY . .
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/dist /bin/www

COPY nginx/nginx-default.conf.template /etc/nginx/conf.d/default.conf.template

COPY nginx/docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
