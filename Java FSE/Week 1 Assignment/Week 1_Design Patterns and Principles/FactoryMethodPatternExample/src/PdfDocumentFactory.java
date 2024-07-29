public class PdfDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String content) {
        return new PdfDocument(content);
    }
}