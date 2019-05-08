@Service
public class ProdnOrdService {
  // **********************************************************************
  // *   Konfiguration des SAP S/4HANA Kontextes
  // **********************************************************************
  private static final ErpConfigContext erpConfigContext =
      new ErpConfigContext(
          ConstantValues.DESTINATION_NAME,
          new SapClient(ConstantValues.CLIENT));
  // **********************************************************************
  // * Konfiguration eines Dienste für die Fertigungsaufträge mit Hilfe der
  // * SAP S/4HANA Cloud SDK, um die S/4HANA Cloud OData-APIs anzusprechen
  // * a ProductionOrderService from the SDK.
  // **********************************************************************
  private static final ProductionOrderService productionOrderService =
            new DefaultProductionOrderService();
  // **********************************************************************
  // * Lade alle Fertigungsauftäge
  // **********************************************************************
  public List<ProductionOrder> getAllProdnOrds() throws SAPODataException {
    try {
      return productionOrderService.getAllProductionOrder().
              execute(erpConfigContext);
    } catch (ODataException e) {
      throw SAPODataException.error(SAPMessage.NOT_FOUND, e);
    }
}