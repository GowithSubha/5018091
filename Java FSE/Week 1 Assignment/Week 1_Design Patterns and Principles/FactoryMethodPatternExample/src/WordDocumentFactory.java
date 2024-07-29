public class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument(String content) {
        return new WordDocument(content);
    }
}