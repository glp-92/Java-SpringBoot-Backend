# Deploy Backend to Kubernetes

## Fast Desployment on Windows locally
1. `yaml` files are generated on the root repository folder. Init `Docker Destop` and enable `Kubernetes` on settings.
2. `kubectl apply -f discovery-server.yaml,api-gateway.yaml,auth-server.yaml,store-server.yaml` to create deployments and services.
3. `NodePort` param is configured to `Discovery-server` and `Api-Gateway` so users can access to test Eureka Dashboard and API endpoints.
    - `Discovery-server` localhost:31000
    - `Api-Gateway` localhost:32000

## Kubernetes overview
Kubernetes is a tool that helps to manage and deploy containerized applications (such as Docker containers) across a cluster of computers or virtualized environments. It takes care of things like load balancing, scaling, and failover.

In Kubernetes, users define desired state of their containerized applications by using a configuration file, and Kubernetes will automatically ensure that the applications are running as expected.

**Main components of Kubernetes:**
1. Pod: smallest unit, usually has one container into it (contains an app). Each POD has an internal IP address. When a POD is replaced, new IP is assigned.
2. Service: because IP of the POD may change, service offers an static IP address and PODs can attach to it. If associated POD goes down, Service IP doesn't change.
3. Ingress: component that forwards typically http traffic from outside the cluster to inside.
4. ConfigMap: contains configurations of the application like db url, discovery...
5. Secret: to store credentials, keys...
6. Volumes: to store persistent data on the cluster, attaches fisical store to POD, locally or cloud. Usually, data persistency is placed outside clusters.
7. StatefulSet: sincronize read, write from PODs to database.


**Kubernetes manages 2 types of nodes:**
1. `Master node`: node that manages cluster by using these services:
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
    - In this case, some config attributes should change, so it is preferred to make custom `yaml`  config files, as the located on root folder of this repo.
3. To create and run the Kubernetes resources specifying `yaml` files:
    ```
    kubectl apply -f api-gateway.yaml,auth-server.yaml,discovery-server.yaml,store-server.yaml
    ```
    :heavy_exclamation_mark: It is important to not have spaces or additional commas between file specification.
4. To **stop cluster** and delete associated containers, on `Docker Desktop`, go to `settings -> kubernetes -> reset kubernetes cluster`

## About yaml configurations
- Parts of config files:
    1. Metadata: typically name of the component.
    2. Spec: configuration to apply to the component.
    3. Status: managed by Kubernetes.

- Deployment example of `discovery-server`: Configuration can be changed while running the cluster and update the application by applying the deployment config.
    ```
    apiVersion: apps/v1
    kind: Deployment
    metadata:
    name: discovery-server
    labels:
        app: discovery-server
    spec:
    replicas: 2
    selector:
        matchLabels:
        app: discovery-server #match with specified template (labels:app:...)
    template:
        metadata:
        labels:
            app: discovery-server
        spec:
        containers:
        - name: discovery-server 
            image: guill392/discovery-service:latest #image on docker hub or local
            ports:
            - containerPort: 8761 #port exposed but container, NOT outside cluster network
    ```
- Service example, in this one, port 31000 of cluster is beeing exposed using NodePort.
    ```
    apiVersion: v1
    kind: Service
    metadata:
        name: discovery-server-service
    spec:
        type: NodePort #Nodeport exposes port to outside of the cluster network
        selector:
            app: discovery-server #refering deployment name specified on deployment kind config (labels:app:...)
        ports:
            - protocol: TCP
            port: 8761 #port by the service to connect other services or pods
            targetPort: 8761 #port forwarded to the POD, must match containerPort on deployment
            nodePort: 31000 #port exposed outside, kubernetes forces >30000
    ```

## Troubleshooting
- Forward a service running on Kubernetes:
    1. Get POD name `kubectl get pods` 
    2. Forward port of POD `kubectl port-forward podName port:port`

## Useful Kubectl commands
| Command | Usage | 
| --- | --- |
| `kubectl apply -f yamlFiles` | Create resources from list of `yaml` files |
| `kubectl describe svc api-gateway` | Look for IP of a service |
| `kubectl get nodes/pods/services/deployments` | Get status of nodes, pods, services or deployments running |
| `kubectl create/edit/delete deployment deplName` | CRUD of deployments |