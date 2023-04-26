# Deploy Backend to Kubernetes

## Kubernetes overview
Kubernetes is a tool that helps to manage and deploy containerized applications (such as Docker containers) across a cluster of computers. It takes care of things like load balancing, scaling, and failover.

In Kubernetes, users can define the desired state of their containerized applications by using a configuration file, and Kubernetes will automatically ensure that the applications are running as expected.

**Main components of Kubernetes:**
1. Pod: smallest unit, usually has one container into it (contains an app). Each POD has an internal IP address. When a POD is replaced, new IP is assigned.
2. Service: because IP of the POD may change, service offers an static IP address and PODs can attach to it. If associated POD goes down, Service IP doesn't change.
3. Ingress: component that forwards typically http traffic from outside the cluster to inside.
4. ConfigMap: contain configurations of the application like db url, discovery...
5. Secret: to store credentials, keys...
6. Volumes: to persist data on the cluster, attaches fisical store to POD, locally on cloud. Usually, data persistency is placed outside clusters.
7. StatefulSet: sincronize read, write from PODs to database.


**Kubernetes manages 2 types of nodes:**
1. `Master node`: manage cluster by using these services:
    - `kube-scheduler` execs programmed tasks.
    - `kube-control-manager` runs the cluster
    - `kube-apiserver` communicates master with workers.
2. `Worker node`: contains the APP.
    `kubelet`: service that communicates with `kube-apiserver`, each `kubelet` has a `POD`.

**Abstraction layers of Kubernetes:** `Deployment => controls => ReplicaSet => controls => POD`

**Type of scalling in Kubernetes:**
- Horizontal: by adding `PODs` on-demmand.
- Vertical: by adding more clusters.

## Run Kubernetes on Windows
1. [Docker Desktop](https://www.docker.com/products/docker-desktop/) is needed. Go to settings, and enable Kubernetes.
2. A Kubernetes `yaml` manifests files are needed, they describe sources to be created like volumes, pods and services. The easiest way to generate a manifest is by using a `docker-compose.yml` file that is already used to deploy all containers by once in this repo.
    - Kompose is highly used to make this. Easiest way by creating a docker image:
        ```
        docker build -t kompose https://github.com/kubernetes/kompose.git#main
        ```
    - To generate the manifests, go where `docker-compose.yml` file is placed, and run on Windows:
        ```
        docker run --rm -it -v ${PWD}:/opt kompose sh -c "cd /opt && kompose convert"
        ```
3. To create and run the Kubernetes resources specifying `yaml` files:
    ```
    kubectl apply -f api-gateway-deployment.yaml,api-gateway-service.yaml,auth-service-deployment.yaml,discovery-service-deployment.yaml,discovery-service-service.yaml,opt-default-networkpolicy.yaml,store-service-deployment.yaml
    ```
    :heavy_exclamation_mark: It is important to not have spaces or additional commas between file specification.
4. To **stop cluster** and delete associated containers, on `Docker Desktop`, go to `settings -> kubernetes -> reset kubernetes cluster`

## Troubleshooting
- Forward a service running on Kubernetes:
    1. Get POD name `kubectl get pods' 
    2. Forward port `kubectl port-forward podName port:port`
- Eureka clients must modify eureka IP in order to be discovered. To make this:
    ```
    kubectl get pods
    ```

## About yaml configurations
- Parts of config files:
    1. Metadata: typically name of the component.
    2. Spec: configuration to apply to the component.
    3. Status: managed by Kubernetes.

- Deployment example: Configuration can be changed while running the cluster and update the application by applying the deployment config.
    ```
    apiVersion: apps/v1
    kind: Deployment #type of component is beeing created
    metadata:
        name: appname-deployment #name of deployment
        labels:
            app: appname #label used by service selector to connect service to deployment
    spec: *spec for deployment*
        replicas: 2 #number of replicas of the instance
        selector:
            matchLabels:
                app: appname #deployment is told to match the template labels named appname
        template: #the blueprint for the POD
            metadata:
                labels:
                    app: appname
            spec: #spec for the POD
                containers:
                -   name: appname
                    image: appname:latest #name of image to use in the POD
                    ports:
                    -   containerPort: 80 #port binded by POD
    ```
- Service example:
    ```
    apiVersion: v1
    kind: Service #type of component is beeing created 
    metadata:
        name: appname-service #name of deployment
    spec: #spec for service
        selector:
            app: appname
        ports:
            -   protocol: TCP
                port: 80
                targetPort: 8080 #should match containerPort
    ```


## Useful Kubectl commands
| Command | Usage | 
| --- | --- |
| `kubectl apply -f yamlFiles` | Create resources from list of `yaml` files |
| `kubectl describe svc api-gateway` | Look for IP of a service |
| `kubectl get nodes/pods/services/deployments` | Get status of nodes, pods, services or deployments running |
| `kubectl create/edit/delete deployment deplName` | CRUD of deployments |