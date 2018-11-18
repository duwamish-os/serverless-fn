package event.handlers

import java.util.UUID

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}
import event.db.Database

class InventoryEventHandler extends RequestHandler[InventoryReq, InventoryResp] {

  import InventoryEventHandler._

  override def handleRequest(req: InventoryReq, context: Context): InventoryResp = {

    val inventory = Database.inventoryByItem(req.getItem)

    new InventoryResp(
      apiId,
      req.getItem,
      inventory.map(_.qty).getOrElse(0)
    )
  }

}

object InventoryEventHandler {
  val handler = new InventoryEventHandler()
  private val apiId = UUID.randomUUID()

  def main(args: Array[String]): Unit = {

    val req = new InventoryReq(
      UUID.randomUUID(),
      "sku-1",
      100)

    val resp = handler.handleRequest(req, null)
    println(resp.getItem)
    println(resp.getTotalQty)
  }

}
