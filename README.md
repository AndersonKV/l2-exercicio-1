"# l2-exercicio-1" 

# 📦 Packing Service

Sistema de empacotamento de produtos com validação de dimensões e autenticação via Spring Security. Desenvolvido com Spring Boot, com suporte a execução via Docker.

---

## ✅ Funcionalidades

- 🔐 **Autenticação via Spring Security** (usuário padrão: `admin`, senha: `secret`)
- 🧪 **Validações de campos obrigatórios**
  - Dimensões (altura, largura, comprimento) não podem ser 0
- 📦 Algoritmo para encaixar produtos em caixas automaticamente
- 🔄 **Endpoint principal:** `POST /api/v1/packing`
- 🧪 **Testes E2E incluídos**
-  Projeto em Microserviço (sobe, responde e desliga sem depender de outro monolito)
---

## 🚀 Como rodar o projeto com Docker (Windows)

### 1. **Pré-requisitos**

- Docker instalado (ex: Docker Desktop)

No terminal, execute:

Construir imagem: docker build -t packing-service .
Rodar container: docker run -p 8080:8080 packing-service

A aplicação estará acessível em:
Swagger: http://localhost:8080/swagger-ui.html

Exemplo de entrada

{
  "pedidos": [
    {
      "pedido_id": 1,
      "produtos": [
        { "produto_id": "PS5",    "dimensoes": { "altura": 40, "largura": 10, "comprimento": 25 } },
        { "produto_id": "Volante","dimensoes": { "altura": 40, "largura": 30, "comprimento": 30 } }
      ]
    }
  ]
}


Exemplo de saída

{
  "pedidos": [
    {
      "pedido_id": 1,
      "caixas": [
        {
          "caixa_id": "Caixa 1",
          "produtos": [
            "Volante",
            "PS5"
          ],
          "observacao": null
        }
      ]
    }
  ]
}


