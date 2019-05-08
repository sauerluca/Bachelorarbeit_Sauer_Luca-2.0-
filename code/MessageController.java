@RestController
@RequestMapping("/")
public class MessageController {
    private final EventMessagingService messagingService;
    //**********************************************************************
    //*   Instanziieren des Event Messaging Services
    //**********************************************************************
    public MessageController(
            EventMessagingService messagingService) {
        this.messagingService = messagingService;
    }
    //**********************************************************************
    //*   Konfiguration des Endpunkts der Schnittstelle
    //**********************************************************************
    @GetMapping("/listen")
    public void startReceiving() throws MessagingException {
        messagingService.startReceiving();
    }
}