public class WordDocument extends Document {
    public WordDocument(String content) {
        super(content);
    }

    @Override
    public void display() {
        System.out.println("Displaying Word document: " + content);
    }
}