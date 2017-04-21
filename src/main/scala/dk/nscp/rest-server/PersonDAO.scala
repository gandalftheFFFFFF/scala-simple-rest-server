package dk.nscp.rest_server

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// Slick
import slick.driver.SQLiteDriver.api._
import org.sqlite.JDBC

case class Person(id: Int, name: String, age: Int)
case class PersonList(persons: Seq[Person])

case class Persons(tag: Tag) extends Table[Person](tag, "PERSONS") {
  def id = column[Int]("ID")
  def name = column[String]("NAME")
  def age = column[Int]("AGE")
  def * = (id, name, age) <> (Person.tupled, Person.unapply)
}

object PersonsDAO extends TableQuery(new Persons(_)) {

val db = Database.forURL("jdbc:sqlite:./person.db")

  def allPersons: Future[Seq[Person]] = {
    db.run(this.result)
  }

  def singlePerson(id: Int): Future[Option[Person]] = {
    db.run(this.filter(_.id === id).result.headOption)
  }
}

