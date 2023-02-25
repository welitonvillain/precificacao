# Angeloni - Preços Server

Servidor de integração para importação de planilhas de preços (precificação)

## Tecnologias

* Java/JDK 8
* Spring Boot 2.x
* Lombok
* Thymeleaf
* Maven

## Instruções

### Alterar as configurações

Todas as configurações estão disponíveis no arquivo `src/main/resources/application.yml` e podem 
ser [alteradas externamente via parâmetro ou variável de ambiente](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html).

Exemplo configuração externa:
`java -jar target/precos-server.jar --spring.config.location=classpath:/application.yml,file:/externa.yml`

_Nota: Arquivos de logs são gerados no diretório `logs` e automaticamente compactados todos os dias._

### Iniciar o servidor Oracle para desenvolvimento e testes

`docker run -d -p 1521:1521 -e ORACLE_ALLOW_REMOTE=true -e ORACLE_DISABLE_ASYNCH_IO=true oracleinanutshell/oracle-xe-11g`

### Construir e testar

`./mvnw clean package`

_Nota: Adicione `-DskipTests` para ignorar a fase de testes._

### Iniciar o servidor em modo de desenvolvimento

`java -jar target/precos-server.jar --spring.profiles.active=dev`

Usuários: `teste/teste` ou `admin/admin`

Obs: Oracle e LDAP local.

### Iniciar o servidor em modo de produção

`java -jar target/precos-server.jar --spring.profiles.active=prod`

_Nota: O profile de produção está sendo ativado no comando acima._

### Estrutura de tabelas e dados de testes e produção

[src/main/resources/ddl.sql](src/main/resources/ddl.sql)
[src/main/resources/import.sql](src/main/resources/import.sql)

### Gerar stubs dos webservices do ATG (caso o webservice seja modificado)

```
wsimport src/main/resources/wsdl/promotions.wsdl -b src/main/resources/wsdl/promotions.xsd -s src/main/java -Xnocompile -p br.com.angeloni.ws
wsimport src/main/resources/wsdl/versionedAssets.wsdl -b src/main/resources/wsdl/versionedAssets.xsd -s src/main/java -Xnocompile -p br.com.angeloni.ws
```

### Importar certificado SSL do servidor LDAP do Angeloni no truststore do Java

```
keytool -printcert -sslserver ANGCUAAD01.angeloni.com.br:636 -rfc >angeloni-ldap.pem
keytool -import -noprompt -trustcacerts -alias angeloni -file angeloni-ldap.pem -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit
keytool -list -v -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit | grep angeloni
```
