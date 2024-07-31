public class ComputerBuilderTest {
        public static void main(String[] args) {
                Computer lowLevelComputer = new Computer.Builder()
                                .setCpu("Intel Core i3")
                                .setRam(8)
                                .setStorage("500GB HDD")
                                .build();
                System.out.println(
                                "Office level Computer: " + lowLevelComputer.getCpu() + ", " + lowLevelComputer.getRam()
                                                + "GB RAM, "
                                                + lowLevelComputer.getStorage());

                Computer highLevelComputer = new Computer.Builder()
                                .setCpu("Amd RYZEN 9 5900X")
                                .setRam(32)
                                .setStorage("1TB SSD")
                                .setHasGraphicsCard(true)
                                .build();
                System.out.println("Higher Processing Computer: " + highLevelComputer.getCpu() + ", "
                                + highLevelComputer.getRam()
                                + "GB RAM, "
                                + highLevelComputer.getStorage() + ", Graphics Card: "
                                + highLevelComputer.isHasGraphicsCard());

                Computer serverLevelComputer = new Computer.Builder()
                                .setCpu("AMD Opteron 6276")
                                .setRam(32)
                                .setStorage("8TB HDD")
                                .setHasSSD(true)
                                .build();
                System.out.println("Server Computer: " + serverLevelComputer.getCpu() + ", "
                                + serverLevelComputer.getRam() + "GB RAM, "
                                + serverLevelComputer.getStorage() + ", SSD: " + serverLevelComputer.isHasSSD());
        }
}