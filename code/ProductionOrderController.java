public class ProductionOrderController {

  private static final Logger logger =
      CloudLoggerFactory.getLogger(ProductionOrderController.class);

  private static final ProductionOrderService productionOrderService =
      new DefaultProductionOrderService();

  // **********************************************************************
  // *   Zugriff auf den Fertigungsauftrag aus der Datenbank
  // **********************************************************************
  public static Try<ProductionOrder> lookupProductionOrder(
          final String productionOrderKey) {
    return Try.of(
        () ->
            new JwtBasedRequestContextExecutor()
                .withXsuaaServiceJwt()
                .execute(
                    () -> {
                      final ProductionOrder productionOrder =
                          productionOrderService
                              .getProductionOrderByKey(productionOrderKey)
                              .execute();
                      logger.info("Fetched Production Order " + productionOrderKey);
                      logger.info("Production Order: " + productionOrder);
                      return productionOrder;
                    }));
  }
}