FROM harbor.bna.local/image_repo/nginx:stable-alpine3.23

RUN mkdir -p /usr/share/nginx/html/upload

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY dist/ /usr/share/nginx/html

EXPOSE 85
CMD ["nginx", "-g", "daemon off;"]
