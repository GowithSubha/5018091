public class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    private boolean isLoaded = false;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (!isLoaded) {
            realImage = new RealImage(filename);
            isLoaded = true;
        }
        realImage.display();
    }
}