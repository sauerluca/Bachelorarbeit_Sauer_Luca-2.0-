@SpringBootApplication
@ComponentScan({"com.sap.cloud.sdk", "com.cloud.s4hana"})
@ServletComponentScan({"com.sap.cloud.sdk", "com.cloud.s4hana"})
public class Application extends SpringBootServletInitializer {
  //**********************************************************************
  //*   Starten der Spring Applikation
  //**********************************************************************
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(
          final SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }
}