package event.db

import cats.effect.{ContextShift, IO}
import doobie.util.transactor.Transactor
import doobie.util.transactor.Transactor.Aux

import scala.concurrent.{ExecutionContext, Future}
import doobie._
import doobie.implicits._
import cats._
import cats.implicits._
import com.amazonaws.services.lambda.runtime.LambdaRuntime

object Database {

  private val logger = LambdaRuntime.getLogger

  case class Inventory(sku: String, qty: Int)

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.Implicits.global)

  private val driver = "com.mysql.cj.jdbc.Driver"
  private val connectionUrl = "jdbc:mysql://duwamish.<<12>>.us-west-2.rds.amazonaws.com:3306/inventorydb?connectTimeout=1500"
  private val user = "root"
  private val password = "donnyjepp"

  logger.log(s"getting connection to $connectionUrl")

  lazy val connection: Aux[IO, Unit] = Transactor.fromDriverManager[IO](
    driver = driver,
    url = connectionUrl,
    user = user,
    pass = password
  )

  def inventoryByItem(sku: String): Option[Inventory] = {
    logger.log(s"finding inventory for $sku")

    sql"""select sku, qty from Inventory where sku=$sku"""
      .query[Inventory]
      .option
      .transact(connection)
      .unsafeRunSync()
  }
}
