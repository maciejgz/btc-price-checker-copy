## Description
POC of the following cases: </br>
- redis distributed cache on the Spring service level
- spring boot one instance locks - JPA optimistic lock, Spring Transactional lock serializable, reentrant lock
- distributed lock with Spring Integration and Redis cluster
- Hibernate different Level caches

## Deploy
Deploy to heroku with: `mvnw clean compile package heroku:deploy`

## Redis
Run docker compose using /docker/btc-price-checker-compose.yml </br>
When all containers are up, execute command on the redis-node-5 container: </br>
```
redis-cli --cluster create redis-node-0:6379 redis-node-1:6379 redis-node-2:6379 redis-node-3:6379 redis-node-4:6379 redis-node-5:6379 --cluster-replicas 1 --cluster-yes
```

TODO: 
- +ustawić cache spring nawet na jednym node
- +test redis cache - [BtcPriceRestController/eth](src/main/java/pl/mg/btc/controller/BtcPriceRestController.java)
- +dodać zapis wartości do bazy danych przy użyciu JPA
- +optimistic locking - JPA level by @Version field in [Currency class](src/main/java/pl/mg/btc/currency/CurrencyEntity.java)
- +pesimistic locking - ziamplementować pesimistic lock na jednej instancji - jeśli dwie aplikacje korzystają z jednej bazy to transactional serializable pozwala na zaimplementowanie pessimistic lock: [BtcPriceRestController/currency](src/main/java/pl/mg/btc/controller/BtcPriceRestController.java)
- +distributed lock on the service method - distributed lock bazujący na rozwiązaniach springowych (lockRegistry) z wykorzystaniem redisa nałożony na metody serwisowe: [DistributedLockController.java](src/main/java/pl/mg/btc/controller/DistributedLockController.java)
- symulacja koszyka i transakcji składania zamówienia na kilku instancjach połączonych w klaster dockerowy/k8s - pakiet pl.mg.btc.store
- problem nowej transakcji na obiekcie uruchamianej w  asynchronicznej
- testy z 1st level cachem hibernate
- testy z 2nd level cachem hibernate