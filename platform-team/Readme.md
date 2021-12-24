

## Jenkins installation

docker network create jenkins

docker run \
  --name jenkins-docker \
  --rm \
  --detach \
  --privileged \
  --network jenkins \
  --network-alias docker \
  --env DOCKER_TLS_CERTDIR=/certs \
  --volume jenkins-docker-certs:/certs/client \
  --volume jenkins-data:/var/jenkins_home \
  --publish 2376:2376 \
  docker:dind \
  --storage-driver overlay2

docker build -t myjenkins-blueocean:1.1 .

docker run --name jenkins-blueocean --rm --detach \
  --network jenkins --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 \
  --publish 8080:8080 --publish 50000:50000 \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  myjenkins-blueocean:1.1
  

docker exec -it jenkins-blueocean bash
cat /var/jenkins_home/secrets/initialAdminPassword

docker stop jenkins-blueocean 

docker stop jenkins-docker




### install and run sonarqube
docker pull sonarqube:latest
docker run -d -p 9000:9000  -v sonarqube_extensions:/opt/sonarqube/extensions --name sonarqube sonarqube:latest
curl http://localhost:9000 (admin/admin to start)

### install and run nexus
docker pull sonatype/nexus3
docker run -d -p 8081:8081 --name nexus sonatype/nexus3
curl http://localhost:8081/

##First time admin password is saved under /nexus-data/admin.password file in docker containerId
docker ps
docker exec -it <containerId> /bin/sh


## Setup kubernetes objects for services.

minikube start

kubectl apply -f k8s/platform-app-config/ops/ops-tools-api/platform-secret.yaml
kubectl apply -f k8s/platform-app-config/ops/ops-tools-api/platform-configmap.yaml
kubectl apply -f k8s/platform-app-config/ops/ops-tools-api/platform-dev01-configmap.yaml

kubectl apply -f k8s/ops-app-config/ops-tools-api/application-configmap.yaml
kubectl apply -f k8s/ops-app-config/ops-tools-api/application-dev01-configmap.yaml
kubectl apply -f k8s/ops-app-config/ops-tools-api/application-default-configmap.yaml

## Install the apis locally.
    
kubectl apply -f k8s/plaform-cd/ops/ops-tools-api/deployment-local.yaml
