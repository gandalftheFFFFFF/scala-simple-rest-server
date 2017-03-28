# Simple server with simple REST API

Run the server with `sbt run`. The server will run on `localhost:8080`.

Use `curl` to access the 'api':

```bash
$ curl -s -X GET localhost:8080/health
{"status":"Healthy","description":"Initialized"}

$ curl -s -H "Content-Type: application/json" -X POST -d '{"status": "Super healthly", "description": "This is the best server ever"}' localhost:8080/health
Posted health as Super healthly!

$ curl -s -X GET localhost:8080/health
{"status":"Super healthly","description":"This is the best server ever"}
```


