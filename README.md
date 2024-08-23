#  Desafio-Itau

# Resumo

 Este repositorio consiste em uma api com os serviços de CRUD integrada ao git actions e aws
 A api esta armazenada no AWS EC2(book-itau / mas pode ser testada localmente)
 Onde é persistido os dados no AWS RDS(book-db)
 Após a manipulação do banco de dados com as operações SAVE, UPDATE E DELETE 
 É automaticamente enviado uma notificação para um topico SNS(book-topic) pela api via sns publish
 Onde existe uma fila SQS(book-queue) escutando este topico
 Quando uma mensagem chega na fila o lambda(bookAtualizaDB/Python) que esta escutando a fila
 Manipula os dados e envia o log da operação para o S3
 Onde é criado m file para cada operação realizado  naquele mesmo dia
 A cada dia é gerado um novo log de cada operação 
