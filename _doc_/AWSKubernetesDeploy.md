# Deploy Kubernetes Cluster to AWS EKS

Amazon EKS manages Master Nodes, scaling and backup. Worker nodes are the ones that must be deployed.

## Step by Step AWS Console

1. Create IAM role to EKS to give permission to access EC2 instances and VPC. Currently using AWS Academy so this role is predefined under LabRole.
2. Create Security Group to be used by the cluster and expose port 31000 (Eureka) and port 32000 (API Gateway).
    - EC2 => Security Groups.
        - Input rule: TCP - port 31000 - anywhere IPv4 - `Eureka`
        - Input rule: TPC - port 32000 - anywhere IPv4 - `Api-Gateway`
3. EKS => Create cluster.
    - Role: LabRole (provided by Academy) => Next
    - Networking
        - VPC Default.
        - Security Groups: created before.
        - Public Access to Cluster
        - Next.
    - Logging: not enable logging in this moment => Next... => Create
4. 
