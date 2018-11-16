package event.handlers

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

final case class InventoryRequest(item: String, qty: Int)

final case class InventoryResponse(item: String, totalQty: Int)

class InventoryEventHandler extends RequestHandler[InventoryReq, InventoryResp] {

  override def handleRequest(input: InventoryReq, context: Context): InventoryResp = {

    new InventoryResp(
      input.getItem,
      input.getQty + 10
    )
  }

}

object InventoryEventHandler {
  val handler = new InventoryEventHandler()

  def main(args: Array[String]): Unit = {
    val resp = handler.handleRequest(new InventoryReq("item1", 100), null)
    println(resp.getItem)
    println(resp.getTotalQty)
  }

}
