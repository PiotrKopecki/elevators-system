FROM node:latest as builder

WORKDIR /usr/local/app

COPY ./ /usr/local/app/

RUN npm install --legacy-peer-deps
RUN npm run build

FROM nginx:latest

COPY --from=builder /usr/local/app/dist/elevators-ui /usr/share/nginx/html

EXPOSE 4200
