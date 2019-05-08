@Service
public final class EventMessagingService {
  // **********************************************************************
  // *   Öffne die Messaging Instanz der SAP Cloud Platform
  // **********************************************************************
  private static MessagingService getMessagingService() {
    final Cloud cloud = new CloudFactory().getCloud();
    return cloud.getSingletonServiceConnector(MessagingService.class);
  }
  // **********************************************************************
  // *   Starte das Warten auf Ereignisse
  // **********************************************************************
  public void startReceiving() throws MessagingException {
    try {
      final Connection connection = grantConnection();
      final Session session = connection.
              createSession(false, Session.CLIENT_ACKNOWLEDGE);
      final MessageConsumer messageConsumer = getConsumer(session);
      final JmsMessageDelegator delegator = new JmsMessageDelegator(
              new DefaultMessageDelegator());
      delegator.addListener(
              new ProductionOrderReleasedNotificationListener());
      messageConsumer.setMessageListener(delegator);
      connection.start();
    } catch (final JMSException e) {
      throw new MessagingException(e);
    }
  }
  // **********************************************************************
  // *   Eröffne die Verbindung mit der Messagning Instanz
  // **********************************************************************
  private Connection grantConnection() throws MessagingException, JMSException {
    final MessagingServiceFactory messagingServiceFactory =
        MessagingServiceFactoryCreator.createFactory(getMessagingService());
    final MessagingServiceJmsConnectionFactory connectionFactory =
        messagingServiceFactory.createConnectionFactory(
                MessagingServiceJmsConnectionFactory.class);
    return connectionFactory.createConnection();
  }
  // **********************************************************************
  // *   Registrieren auf einen Ereigniskanal
  // **********************************************************************
  private MessageConsumer getConsumer(final Session session) throws JMSException {
    final Queue queue = session.createQueue("queue:" + "EVENT_CHANNEL");
    return session.createConsumer(queue);
  }
}