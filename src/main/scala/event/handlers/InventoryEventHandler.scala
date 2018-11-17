package event.handlers

import java.util.UUID

import com.amazonaws.services.lambda.runtime.{Context, RequestHandler}

class InventoryEventHandler extends RequestHandler[InventoryReq, InventoryResp] {

  import InventoryEventHandler._

  override def handleRequest(input: InventoryReq, context: Context): InventoryResp = {

    new InventoryResp(
      apiId,
      input.getItem,
      input.getQty + 10
    )
  }

}

object InventoryEventHandler {
  val handler = new InventoryEventHandler()
  private val apiId = UUID.randomUUID()

  def main(args: Array[String]): Unit = {
    val resp = handler.handleRequest(new InventoryReq(UUID.randomUUID(), "item1", 100), null)
    println(resp.getItem)
    println(resp.getTotalQty)
  }

}
