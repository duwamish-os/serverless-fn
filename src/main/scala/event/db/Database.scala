package event.db

import cats.effect.{ContextShift, IO}
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux

import scala.concurrent.{ExecutionContext, Future}
import doobie._
import doobie.implicits._
import cats._
import cats.implicits._

object Database {

  case class Inventory(sku: String, qty: Int)

  implicit val cs = IO.contextShift(ExecutionContext.Implicits.global)

  val connection: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    driver = "com.mysql.cj.jdbc.Driver",
    url = "jdbc:mysql://duwamish.<<12>>.us-west-2.rds.amazonaws.com:3306/inventorydb",
    user = "root",
    pass = "deep"
  )

  def inventoryByItem(sku: String): Option[Inventory] = {
    sql"""select sku, qty from Inventory where sku=$sku"""
      .query[Inventory]
      .option
      .transact(connection)
      .unsafeRunSync()
  }
}
