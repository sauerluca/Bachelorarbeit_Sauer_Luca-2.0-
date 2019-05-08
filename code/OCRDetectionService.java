public class OCRDetectionService {
  /**
   * Erkennt den Text eines Bildes
   *
   * @param base64String Der Base64 String des Bildes.
   * @param credentialsPath Die Login Daten.
   * @throws Exception Fehler auf Client Seite.
   * @throws IOException Input/Output Fehler.
   */
  public static BatchAnnotateImagesResponse
    detectText(String base64String, String credentialsPath)
      throws Exception, IOException {
    
    List<AnnotateImageRequest> requests = new ArrayList<>();
    
    // Read from Base64
    String base64Image = base64String.split(",")[1];
    InputStream stream = new ByteArrayInputStream(Base64.decodeBase64(base64Image));
    ByteString imgBytes = ByteString.readFrom(stream);
    Image img = Image.newBuilder().setContent(imgBytes).build();

    // Set credentials
    ImageAnnotatorSettings settings =
        ImageAnnotatorSettings.newBuilder()
            .setCredentialsProvider(
                FixedCredentialsProvider.create(
                    GoogleCredentials.fromStream(
                        new ClassPathResource(credentialsPath).getInputStream())))
            .build();

    Feature feat = Feature.newBuilder().setType(
            Feature.Type.DOCUMENT_TEXT_DETECTION).build();

    requests.add(
        AnnotateImageRequest.newBuilder().
                addFeatures(feat).setImage(img).build());

    try (ImageAnnotatorClient client =
                 ImageAnnotatorClient.create(settings)) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      return response;
    }
  }
}