# API para bibliotecas escolares

## Ideia

A ideia do projeto surgiu a partir de uma experiência pessoal ao tentar pegar um livro emprestado na biblioteca da escola, onde todo o controle de empréstimos era realizado manualmente, utilizando papel e contando com a boa-fé dos usuários.

Ao perceber a fragilidade e a ineficiência desse processo, surgiu a proposta de desenvolver uma aplicação que modernizasse a gestão da biblioteca escolar.

A API permitirá que os alunos pesquisem os livros disponíveis antes mesmo de se dirigirem à biblioteca. Dessa forma, ao chegar para solicitar o empréstimo, o bibliotecário(a) precisará apenas registrar a operação no sistema, tornando o processo mais rápido, organizado e seguro.

Além de otimizar o controle de empréstimos, a solução contribui para:

Redução de erros e extravios

Melhor organização do acervo

Maior transparência no controle de devoluções

Incentivo à leitura por meio de um acesso facilitado às informações

O objetivo é transformar um processo manual e vulnerável em um sistema digital eficiente, promovendo tecnologia e organização no ambiente escolar.

## Da API
# 🔐 Usuários

### 📌 Listar usuários
```http
GET /usuarios
```

### 📌 Buscar usuário por ID
```http
GET /usuarios/{id}
```

### 📌 Criar usuário
```http
POST /usuarios
```

### 📌 Atualizar usuário
```http
PUT /usuarios/{id}
```

### 📌 Deletar usuário
```http
DELETE /usuarios/{id}
```

### 📌 Login
```http
POST /usuarios/login
```

### 📌 Validar conta
```http
POST /usuarios/validar
```

### 📌 Recuperar senha
```http
POST /usuarios/recuperar-senha
```

### 📌 Alterar senha
```http
PATCH /usuarios/alterar-senha
```

### 📌 Buscar usuários
```http
GET /usuarios/buscar
```

---

# 📖 Livros

### 📌 Listar livros
```http
GET /livros
```

### 📌 Buscar livro por ID
```http
GET /livros/{id}
```

### 📌 Cadastrar livro
```http
POST /livros
```

### 📌 Atualizar livro
```http
PUT /livros/{id}
```

### 📌 Remover livro
```http
DELETE /livros/{id}
```

### 📌 Buscar livros
```http
GET /livros/buscar
```

---

# 📦 Empréstimos

### 📌 Listar empréstimos
```http
GET /emprestimos
```

### 📌 Buscar empréstimo por ID
```http
GET /emprestimos/{id}
```

### 📌 Criar empréstimo
```http
POST /emprestimos
```

### 📌 Atualizar empréstimo
```http
PUT /emprestimos/{id}
```

### 📌 Remover empréstimo
```http
DELETE /emprestimos/{id}
```

### 📌 Renovar empréstimo
```http
PATCH /emprestimos/renovar/{id}
```

### 📌 Devolver livro
```http
PATCH /emprestimos/devolver/{id}
```

### 📌 Remarcar devolução
```http
PATCH /emprestimos/remarcar/{id}
```

### 📌 Verificar empréstimo
```http
GET /emprestimos/verificar/{id}
```

### 📌 Listar empréstimos por usuário
```http
GET /emprestimos/usuario
```

### 📌 Pesquisar empréstimos do usuário
```http
GET /emprestimos/usuario/pesquisar
```

### 📌 Buscar empréstimos
```http
GET /emprestimos/buscar
```

---

# 💰 Multas

### 📌 Pagar multa
```http
POST /multa/pagar
```

### 📌 Remover multa
```http
POST /multa/remover
```

---

# 🏫 Escola

### 📌 Listar escolas
```http
GET /escolas
```

---

# 🔑 Código

### 📌 Validar código
```http
GET /codigo/validar
```

---

# 🔐 Autenticação

A API utiliza autenticação baseada em token JWT.

Para acessar endpoints protegidos:

```
Authorization: Bearer {seu_token}
```

---

# 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Springdoc OpenAPI (Swagger)

---

# 📄 Documentação Swagger

Após iniciar a aplicação:

```
http://localhost:8080/swagger-ui/index.html
```