package dk.nscp.rest_server

import io.getquill._

import scala.concurrent.Future


case class Person(id: Int, name: String, age: Int)
case class PartialPerson(name: String, age: Int)
case class PersonList(persons: Seq[Person])
case class Error(data: String, message: String)

object PersonsDAO {

  implicit val ec = scala.concurrent.ExecutionContext.global

  val ctx = new PostgresAsyncContext(SnakeCase, "ctx")
  import ctx._

  /* Create a mapping between column names in postgres and the Person case class */
  val person = quote {
    querySchema[Person](
      "person",
      _.id -> "person_id",
      _.age -> "birth_year",
    )
  }

  /* Query for all people in the db */
  def allPersons: Future[Seq[Person]] = {
    ctx.run(person)
  }

  /* Query for a single person */
  def singlePerson(id: Int): Future[Option[Person]] = {
    ctx.run(person.filter(_.id == lift(id))).map(_.headOption)
  }

  def addPerson(name: String, age: Int): Future[Person] = {
    ctx.run(person.insert(_.name -> lift(name), _.age -> lift(age)).returning(p => p))
  }

  /* Delete person by id */
  def deletePerson(id: Int): Future[Int] = {
    ctx.run(person.filter(_.id == lift(id)).delete).map(_ => 0) // This feels a little awkward :(
  }
}

