package event.handlers

import java.util.UUID

import com.amazonaws.services.lambda.runtime._
import event.db.Database

class InventoryEventHandler extends RequestHandler[InventoryReq, InventoryResp] {

  import InventoryEventHandler._
  import scala.concurrent.ExecutionContext.Implicits.global

  override def handleRequest(req: InventoryReq, context: Context): InventoryResp = {

    context.getLogger.log(s"request: ${req.getItem}")

    val inventory =
      if (req.isUseDb) {
        Database
          .inventoryByItem(req.getItem)
          .map(_.qty)
          .getOrElse(0)

      } else {
        req.getQty
      }

    new InventoryResp(
      apiId,
      req.getItem,
      inventory
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
      100,
      true
    )

    val resp = handler.handleRequest(req, new Context {
      override def getAwsRequestId: String = ???

      override def getLogGroupName: String = ???

      override def getLogStreamName: String = ???

      override def getFunctionName: String = ???

      override def getFunctionVersion: String = ???

      override def getInvokedFunctionArn: String = ???

      override def getIdentity: CognitoIdentity = ???

      override def getClientContext: ClientContext = ???

      override def getRemainingTimeInMillis: Int = ???

      override def getMemoryLimitInMB: Int = ???

      override def getLogger: LambdaLogger = LambdaRuntime.getLogger
    })

    println("Item: " + resp.getItem)
    println("Qty: " + resp.getTotalQty)
  }

}
