# 📦 Web Service SOAP - Produtos

Projeto desenvolvido em **Java + Spring Boot** com **SOAP**.

O serviço recebe o **código de um produto** e retorna:

- Nome
- Descrição
- Marca
- Quantidade em estoque

---

## ▶️ Como executar

### 1. Abrir o projeto

Abra a pasta do projeto no VS Code.

### 2. Compilar

No terminal, execute:

```powershell
.\mvnw.cmd clean compile
```

### 3. Iniciar o Web Service

```powershell
.\mvnw.cmd spring-boot:run
```

O projeto será iniciado na porta **8081**, caso use outra porta altere em **src/main/resources/application.properties** .

---

## 🌐 WSDL

Com o projeto rodando, acesse:

```text
http://localhost:8081/ws/produtos.wsdl
```

Se o XML do WSDL aparecer, o serviço está funcionando.

---

## 🧪 Testando no SoapUI

1. Abra o **SoapUI**
2. Crie um **New SOAP Project**
3. Informe o WSDL:

```text
http://localhost:8081/ws/produtos.wsdl
```

4. Abra a operação `consultarProduto`
5. Informe um código de produto, por exemplo:

```xml
<prod:codigo>1</prod:codigo>
```

6. Clique em **▶ Run**

A resposta retornará os dados do produto.
