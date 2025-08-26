data "aws_eks_cluster" "hacka_cluster" {
  name = "hacka_cluster"
}

data "aws_eks_cluster_auth" "hacka_cluster_auth" {
  name = data.aws_eks_cluster.hacka_cluster.name
}

