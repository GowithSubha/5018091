public class ComputerBuilderTest {
    public static void main(String[] args) {
        // Create a basic computer
        Computer basicComputer = new Computer.Builder()
                .setCpu("Intel Core i3")
                .setRam(8)
                .setStorage("500GB HDD")
                .build();
        System.out.println("Basic Computer: " + basicComputer.getCpu() + ", " + basicComputer.getRam() + "GB RAM, "
                + basicComputer.getStorage());

        // Create a gaming computer
        Computer gamingComputer = new Computer.Builder()
                .setCpu("Intel Core i7")
                .setRam(16)
                .setStorage("1TB SSD")
                .setHasGraphicsCard(true)
                .build();
        System.out.println("Gaming Computer: " + gamingComputer.getCpu() + ", " + gamingComputer.getRam() + "GB RAM, "
                + gamingComputer.getStorage() + ", Graphics Card: " + gamingComputer.isHasGraphicsCard());

        // Create a server computer
        Computer serverComputer = new Computer.Builder()
                .setCpu("Intel Xeon")
                .setRam(32)
                .setStorage("2TB HDD")
                .setHasSSD(true)
                .build();
        System.out.println("Server Computer: " + serverComputer.getCpu() + ", " + serverComputer.getRam() + "GB RAM, "
                + serverComputer.getStorage() + ", SSD: " + serverComputer.isHasSSD());
    }
}