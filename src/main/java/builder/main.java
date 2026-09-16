package builder;

public class main {
    public static void main(String[] args) {
        ComputerConfigurationDirector director = new ComputerConfigurationDirector();

        ComputerConfiguration basic = director.createOffice();
        ComputerConfiguration gaming = director.createGaming();

        System.out.println("BASIC: " + basic);
        System.out.println("GAMING: " + gaming);
        System.out.println("🍌");
    }
}
