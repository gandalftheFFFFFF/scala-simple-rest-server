# Simple server with simple REST API

Run the server with `sbt run`. The server will run on `localhost:8080`.

Use `curl` to access the 'api'. It consist of four simple actions:

`GET /persons` -- get all persons in the database

`GET /persons/id` -- get single person by id

`POST /persons` -- add new person

`DELETE /persons/id` -- delete person by id

## Full examples:

### Get all persons

```
$ curl -w "\n" -X GET localhost:8080/persons
{"persons":[{"id":1,"name":"niels","age":28},{"id":2,"name":"laura","age":29},{"id":3,"name":"allan","age":68},{"id":4,"name":"merete","age":67},{"id":5,"name":"asser","age":34},{"id":6,"name":"sofie","age":31}]}
```

### Get a particular person (by id):

```
$ curl -w "\n" -X GET localhost:8080/persons/1
{"id":1,"name":"niels","age":28}
```

### Add a person (providing name and age):

```
$ curl -w "\n" -X POST -H "Content-Type: application/json" -d '{"name": "mads", "age": 35}' localhost:8080/persons
{"id":19,"name":"mads","age":35}

$ curl -w "\n" -X GET localhost:8080/persons
{"persons":[{"id":1,"name":"niels","age":28},{"id":2,"name":"laura","age":29},{"id":3,"name":"allan","age":68},{"id":4,"name":"merete","age":67},{"id":5,"name":"asser","age":34},{"id":6,"name":"sofie","age":31},{"id":19,"name":"mads","age":35}]
```

### Delete a person (by id):

```
$ curl -w "\n" -X DELETE localhost:8080/persons/19
Deleted person with id 19

$ curl -w "\n" -X GET localhost:8080/persons/19
No such person!

$ curl -w "\n" -X GET localhost:8080/persons
{"persons":[{"id":1,"name":"niels","age":28},{"id":2,"name":"laura","age":29},{"id":3,"name":"allan","age":68},{"id":4,"name":"merete","age":67},{"id":5,"name":"asser","age":34},{"id":6,"name":"sofie","age":31}]}
```

# References

I feel like I should mention Yaroslav Tkachenko for providing the only sane
Slick example online. Without him, my `PersonsDAO` wouldn't have been the same!

http://sap1ens.com/blog/2015/07/26/scala-slick-3-how-to-start/

