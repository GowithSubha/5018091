public class ExcelDocument extends Document {
    public ExcelDocument(String content) {
        super(content);
    }

    @Override
    public void display() {
        System.out.println("Displaying Excel document: " + content);
    }
}