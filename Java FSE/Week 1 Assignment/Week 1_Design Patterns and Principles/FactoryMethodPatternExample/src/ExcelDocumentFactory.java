public class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String content) {
        return new ExcelDocument(content);
    }
}