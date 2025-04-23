# Lambda Consulta Vídeos

Essa função Lambda foi desenvolvida para consultar vídeos processados de um determinado usuário na base DynamoDB.

## 📦 Projeto

- Linguagem: Java 17
- Framework: AWS SAM (Serverless Application Model)
- Banco de dados: DynamoDB
- Endpoint protegido por JWT (via API Gateway)

## 📂 Estrutura

- `App.java`: handler principal da Lambda.
- `VideoRepository.java`: classe de acesso ao DynamoDB.
- `VideoDTO.java`: modelo dos dados retornados.
- `VideoResponse.java`: estrutura de resposta com lista de vídeos.

## ▶️ Execução Local

1. Instale o AWS SAM CLI e o Docker.
2. Construa o projeto:
   ```bash
   sam build
   ```
3. Invoque localmente:
   ```bash
   sam local invoke --template .aws-sam/build/template.yaml --event events/event.json
   ```

## 🚀 Deploy

1. Faça o deploy da Lambda com:
   ```bash
   sam deploy --guided --template-file .aws-sam/build/template.yaml
   ```

## 🔐 Autenticação

A Lambda espera o JWT no cabeçalho `Authorization`. O `sub` (subject) do token é usado para buscar os vídeos do usuário no DynamoDB.

## ✅ Exemplo de Resposta

```json
{
  "videos": [
    {
      "nomeVideo": "aula01.mp4",
      "statusProcessamento": "COMPLETO",
      "urlDownload": "https://s3.amazonaws.com/bucket/aula01.mp4"
    }
  ]
}
```

---
