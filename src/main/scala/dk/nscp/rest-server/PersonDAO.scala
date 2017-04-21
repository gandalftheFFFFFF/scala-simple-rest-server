package dk.nscp.rest_server

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// Slick
import slick.driver.SQLiteDriver.api._
import org.sqlite.JDBC

case class Person(id: Int, name: String, age: Int)
case class PartialPerson(name: String, age: Int)
case class PersonList(persons: Seq[Person])

case class Persons(tag: Tag) extends Table[Person](tag, "PERSONS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def age = column[Int]("AGE")
  def * = (id, name, age) <> (Person.tupled, Person.unapply)
}

object PersonsDAO extends TableQuery(new Persons(_)) {

  /* Alternatively create a `val persons = TableQuery[Persons]` instead
   * of using `this` */

  val db = Database.forURL("jdbc:sqlite:./person.db")

  /* Query for all people in the db */
  def allPersons: Future[Seq[Person]] = {
    db.run(this.result)
  }

  /* Query for a single person */
  def singlePerson(id: Int): Future[Option[Person]] = {
    db.run(this.filter(_.id === id).result.headOption)
  }

  /* Adding new person to DB. We need to get the auto incrementing id back
   * with this fancy trick:
   * http://stackoverflow.com/questions/31443505/slick-3-0-insert-and-then-get-auto-increment-value/31448129#31448129
   */
  def addPerson(name: String, age: Int): Future[Person] = { 
    db.run(this returning this.map(_.id) into ((person, id) => person.copy(id = id)) += Person(0, name, age))
  }

  /* Delete person by id */
  def deletePerson(id: Int) = {
    db.run(this.filter(_.id === id).delete)
  }
}

