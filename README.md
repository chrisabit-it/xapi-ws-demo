# xAPI Stock Broker Demo

Just a Quick & Dirty [Ktor](https://ktor.io) Demo Application
for the asynchronous WebSockets-based [xAPI Protocol](http://developers.xstore.pro/documentation/current) used by [xtb](https://www.xtb.com).

## Features

The xAPI protocol is only _rudimentarily_ implemented:

* Retrieval:
  * Version
  * Ping
* Stream:
  * getTickPrices mit sample stockdata Nasdaq 100, Russell 2000, Dow Jones, S&P 500     

## Start Demo

Start Docker Compose - Server listens ws://localhost:8080

```
$ docker compose up -d
```

## Python xAPI Demo Clients

### Prepare

```
$ cd xapi-client-demo
$ python -m venv venv
$ . ./venv/bin/activate
$ pip install -r requirements.txt
```
### Retrieve Demo

Demo retrieval (ping, version) 

```
$ python ./retrieve_demo.py
```

### Stream Demo

Demo streaming (US stock sample tick rates)

```
$ python ./stream_demo.py
```


