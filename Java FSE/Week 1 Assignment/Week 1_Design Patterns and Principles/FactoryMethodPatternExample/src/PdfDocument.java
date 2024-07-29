public class PdfDocument extends Document {
    public PdfDocument(String content) {
        super(content);
    }

    @Override
    public void display() {
        System.out.println("Displaying PDF document: " + content);
    }
}