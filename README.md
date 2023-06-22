# TCC-backend

## Instalação

Para executar o projeto, é necessário ter o docker-compose instalado na máquina, e basta executar o comando abaixo no terminal, tanto no windows quanto no linux

```docker-compose up```

Para rebuildar a imagem do projeto, executar o comando abaixo na pasta raiz:

``docker build -t <nome_do_usuario_docker_hub>/find-services .``

Para rodar somente a aplicação isolada, utilizar o comando:

``docker run -p 8080:8080 -d ``