# Legacy Service

This is a mapper service from old monolith application to new MS structure.

### Setup

#### Local

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
```

#### Docker

```sh
docker build -t quizroulette/legacy-service:v1.0.0 .
docker push quizroulette/legacy-service:v1.0.0
```

#### K8

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: legacy-service-deployment
  labels:
    app: legacy-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: legacy-service
  template:
    metadata:
      labels:
        app: legacy-service
    spec:
      containers:
      - name: legacy-service
        image: quizroulette/legacy-service:v1.0.0
        ports:
        - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
  name: legacy-service-service
spec:
  selector:
    app: legacy-service
  ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
```


#### Other Notes

mvn clean verify

docker build -t quiz-roulette/legacy-server .

docker run -p 33000:8080 quiz-roulette/legacy-server


http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config


AWS Config:

Docker Login
```
aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin 174428063264.dkr.ecr.ap-southeast-1.amazonaws.com
```

Docker Build
```
docker build -t quiz-roulette/legacy-server .
```

Docker Tag
```
docker tag quizroulette/legacy-server:latest 174428063264.dkr.ecr.ap-southeast-1.amazonaws.com/quizroulette/legacy-server:latest
```

Docker Push
```
docker push 174428063264.dkr.ecr.ap-southeast-1.amazonaws.com/quiz-roulette/legacy-server:latest
```


