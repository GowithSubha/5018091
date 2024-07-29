
public class ProxyImageTest {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("image.jpg");
        Image image2 = new ProxyImage("images.jpg");

        image1.display();
        image1.display();
        image2.display();

    }
}