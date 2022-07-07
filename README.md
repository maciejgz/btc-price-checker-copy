## Deploy
Deploy to heroku with: `mvnw clean compile package heroku:deploy`

## Redis
Run docker compose using /docker/btc-price-checker-compose.yml
When all containers are up, execute command on the redis-node-5 container: </br>
```
redis-cli --cluster create redis-node-0:6379 redis-node-1:6379 redis-node-2:6379 redis-node-3:6379 redis-node-4:6379 redis-node-5:6379 --cluster-replicas 1 --cluster-yes
```

TODO: 
- +ustawić cache spring nawet na jednym node
- +test redis cache
- +dodać zapis wartości do bazy danych przy użyciu JPA
- +optimistic locking - JPA level
- pesimistic locking
- distributed lock on the service method
- testy z 1st level cachem
- testy z 2nd level cachem