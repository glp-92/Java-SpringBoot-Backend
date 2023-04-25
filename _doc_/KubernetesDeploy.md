# Deploy Backend to Docker Containers

## Kubernetes overview
Kubernetes is a tool that helps to manage and deploy containerized applications (such as Docker containers) across a cluster of computers. It takes care of things like load balancing, scaling, and failover.

In Kubernetes, users can define the desired state of their containerized applications by using a configuration file, and Kubernetes will automatically ensure that the applications are running as expected.

Kubernetes manages 2 types of nodes:
- `Master node`: manage cluster by using these services:
    - `kube-scheduler` execs programmed tasks.
    - `kube-control-manager` runs the cluster
    - `kube-apiserver` communicates master with workers.
- `Worker node`: contains the APP.
    `kubelet`: service that communicates with `kube-apiserver`, each `kubelet` has a `POD` or the APP, which is instance of containers (where the APP is built)

Type of scalling in Kubernetes:
- Horizontal: by adding `PODs` on-demmand.
- Vertical: by adding more clusters.

## Run Kubernetes on Windows
1. [Docker Desktop](https://www.docker.com/products/docker-desktop/) is needed. Go to settings, and enable Kubernetes.
2. A Kubernetes `yaml` manifests files are needed, they describe sources to be created like volumes, pods and services. The easiest way to generate a manifest is by using a `docker-compose.yml` file that is already used to deploy all containers by once in this repo.
    - Kompose is highly used to make this. Easiest way with creating a docker image:
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

## Useful commands
| Command | Usage | 
| --- | --- |
| `kubectl apply -f yamlFiles` | Create resources from list of `yaml` files |
| `kubectl describe svc api-gateway` | Look for IP of a service |
| `kubectl get nodes` | Get nodes running |
| `kubctl get pods` | Get pods on machine |
| `kubectl get deployments` | Get services running |