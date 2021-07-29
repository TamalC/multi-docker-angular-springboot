FROM node:alpine
USER node

RUN mkdir -p /home/node/app 
WORKDIR /home/node/app

COPY --chown=node:node package.json .

RUN npm install --legacy-peer-deps

COPY --chown=node:node . . 

RUN npm install @angular/cli@11.2.0

CMD ["npm","run","build"]

FROM nginx
EXPOSE 4200
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=0 /home/node/app/dist/ng-jwt-login-client /usr/share/nginx/html
