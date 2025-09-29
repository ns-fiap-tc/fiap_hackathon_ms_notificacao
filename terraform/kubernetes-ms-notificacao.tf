# resource "kubernetes_secret" "secrets-ms-notificacao" {
#   metadata {
#     name = "secrets-ms-notificacao"
#   }

#   type = "Opaque"

#   data = {
#     DB_HOST             = element(split(":",data.aws_db_instance.hacka_db.endpoint),0)
#     DB_PORT             = var.db_hacka_port
#     DB_NAME             = var.db_hacka_name
#     DB_USER             = var.db_hacka_username
#     DB_PASSWORD         = var.db_hacka_password
#   }

#   lifecycle {
#     prevent_destroy = false
#   }
# }

# MS notificacao 
resource "kubernetes_deployment" "deployment-ms-notificacao" {
  metadata {
    name      = "deployment-ms-notificacao"
    namespace = "default"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "deployment-ms-notificacao"
      }
    }

    template {
      metadata {
        labels = {
          app = "deployment-ms-notificacao"
        }
      }

      spec {
        toleration {
          key      = "key"
          operator = "Equal"
          value    = "value"
          effect   = "NoSchedule"
        }

        container {
          name  = "deployment-ms-notificacao-container"
          image = "${var.dockerhub_username}/fiap_hackathon_ms_notificacao:latest"

          resources {
            requests = {
              memory : "512Mi"
              cpu : "500m"
            }
            limits = {
              memory = "1Gi"
              cpu    = "1"
            }
          }

          # env_from {
          #   secret_ref {
          #     name = kubernetes_secret.secrets-ms-notificacao.metadata[0].name
          #   }
          # }

          # Configurações de Observabilidade
          env {
            name  = "MANAGEMENT_METRICS_EXPORT_OTLP_ENDPOINT"
            value = "http://otel-collector:4318/v1/metrics"
          }
          env {
            name  = "MANAGEMENT_METRICS_EXPORT_OTLP_PROTOCOL"
            value = "http/protobuf"
          }
          env {
            name  = "MANAGEMENT_METRICS_TAGS_APPLICATION"
            value = "notificacao-service"
          }
          env {
            name  = "MANAGEMENT_METRICS_TAGS_SERVICE"
            value = "ms-notificacao"
          }
          env {
            name  = "MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE"
            value = "health,info,metrics,prometheus"
          }

          port {
            container_port = "8080"
          }

          # Liveness Probe para verificar se a aplicação está "viva"
          liveness_probe {
            http_get {
              path = "/actuator/health" 
              port = 8080
            }
            initial_delay_seconds = 60 # Espera 120s antes da primeira verificação
            period_seconds        = 10  # Verifica a cada 10s
            timeout_seconds       = 5   # Considera falha se não responder em 5s
            failure_threshold     = 3   # Tenta 3 vezes antes de reiniciar o container
          }

          # Readiness Probe para verificar se a aplicação está pronta para receber tráfego
          readiness_probe {
            http_get {
              path = "/actuator/health"
              port = 8080
            }
            initial_delay_seconds = 60 # Espera 120s antes de marcar como "pronto"
            period_seconds        = 10
            timeout_seconds       = 5
            failure_threshold     = 3
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "service-ms-notificacao" {
  metadata {
    name      = "service-ms-notificacao"
    namespace = "default"
    annotations = {
      "service.beta.kubernetes.io/aws-load-balancer-type" : "nlb",
      "service.beta.kubernetes.io/aws-load-balancer-scheme" : "internal",
      "service.beta.kubernetes.io/aws-load-balancer-cross-zone-load-balancing-enabled" : "true",
      "prometheus.io/scrape" = "true",
      "prometheus.io/port" = "8080",
      "prometheus.io/path" = "/actuator/prometheus"
    }
  }
  spec {
    selector = {
      app = "deployment-ms-notificacao"
    }
    port {
      port = "80"
      target_port = "8080"
    }
    type = "LoadBalancer"
  }
}

# Horizontal Pod Autoscaler (HPA)
resource "kubernetes_horizontal_pod_autoscaler_v2" "hpa-ms-notificacao" {
  metadata {
    name      = "hpa-ms-notificacao"
    namespace = "default"
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = kubernetes_deployment.deployment-ms-notificacao.metadata[0].name
    }

    min_replicas = 1
    max_replicas = 3

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 70 # Escala se o uso médio de CPU passar de 70% do "request"
        }
      }
    }
  }
}