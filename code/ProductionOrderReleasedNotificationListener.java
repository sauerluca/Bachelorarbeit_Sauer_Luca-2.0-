public class ProductionOrderReleasedNotificationListener
    implements ProductionOrderReleasedMessageListener {

  private static final Logger logger =
      CloudLoggerFactory.getLogger(ProductionOrderReleasedNotificationListener.class);

  @Override
  // **********************************************************************
  // *   Warte auf eingehende Ereignisse
  // **********************************************************************
  public void onMessage(@Nonnull final ProductionOrderReleasedMessage message) {
    // **********************************************************************
    // *   Überprüfe das Ereignis
    // **********************************************************************
    if (!message.getManufacturingOrder().isPresent()) {
      logger.error("Received Production Order Released Message.");
      return;
    }
    // **********************************************************************
    // *   Hole das Geschäftsobjekt und benachrichtige den Werker
    // **********************************************************************
    ProductionOrderController.
            lookupProductionOrder(message.getManufacturingOrder().get())
        .onFailure(cause -> 
                logger.error("Exception during Production Order lookup.", cause))
        .onSuccess(
            productionOrder ->
                NotificationController.
                        notifyResponsibleProductionOperator(productionOrder));
  }
}