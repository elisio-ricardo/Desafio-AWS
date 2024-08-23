#  Desafio-Itau

# Resumo

 ## Este repositorio consiste em uma api com os serviços de CRUD integrada ao DockerHub, Git Actions e AWS.
 
 A api esta armazenada no AWS EC2(book-itau / mas pode ser testada localmente),
 onde é persistido os dados no AWS RDS(book-db).
 Após a manipulação do banco de dados com as operações SAVE, UPDATE E DELETE 
 é automaticamente enviado uma notificação para um topico SNS(book-topic) pela api via sns publish,
 onde existe uma fila SQS(book-queue) escutando este topico. 
 Quando uma mensagem chegar na fila o lambda(bookAtualizaDB/Python) que esta escutando
 manipula os dados e envia o log da operação executada pela api para o S3,
 Onde é criado um arquivo para cada operação realizado durante este dia.
 A cada dia é gerado um novo log de cada operação.


 # Git actions

Foi criado uma pipeline no Git actions, onde ela é ativado após um push no repositorio,
esta pipeline consite em:
- Fazer o package da api
- Enviar para o DockerHub
- Buscar a versão latest do dockerHub
- Enviar para o EC2
suas variaveis de ambiente estão salvas no git secret manager

# AWS 
Para utilização é necessario:
- criar o SNS(book-topic)
- Criar o SQS é fazer a assinatura com o SNS
- Criar o RDS(book-db)
- Criar o lambda em python(codigo esta no arquivo na raiz do projeto) e ativar a trigger  com o SQS
- Criar o S3(book-atualizacao-bd-log)

# EC2
É necessario criar uma maquina EC2 e executar o runer do GitHub para ele poder ouvir a pipe do Git Actions.
- Após a criação da maquina virtual siga as instruções do link abaixo para instalação do runner
 https://github.com/elisio-ricardo/Desafio-Itau/actions/runners
 quando criar uma new runner é apresentado o script para executar dentro da maquina ec2

# Observação: é necessario inserir os valores nas variaveis de ambiente, no yml caso rode localmente ou no git secrets caso utilize a pipe 


# SWAGGER

O documento se encontra no http://localhost:8080/swagger-ui.html, após a execução da api localmente
