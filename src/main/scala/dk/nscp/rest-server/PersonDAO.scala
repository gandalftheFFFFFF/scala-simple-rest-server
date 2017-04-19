package dk.nscp.rest_server

// Slick
import slick.driver.SQLiteDriver.api._
import org.sqlite.JDBC

object PersonDAO {

  final case class JsonPerson(id: Int, name: String, age: Int) 

  final case class JsonPersonList(persons: Seq[JsonPerson]) 

  class Person(tag: Tag) extends Table[(Int, String, Int)](tag, "PERSON") {
    def id = column[Int]("id")
    def name = column[String]("name")
    def age = column[Int]("age")
    def * = (id, name, age)
  }


  val db = Database.forURL("jdbc:sqlite:./person.db")

  val persons: TableQuery[Person] = TableQuery[Person]

  def allPersons = db.run(persons.result)
}

