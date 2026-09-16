package builder;

public class main {
    public static void main(String[] args) {
        ComputerConfigurationDirector director = new ComputerConfigurationDirector();

        ComputerConfiguration basic = director.createOffice();
        ComputerConfiguration gaming = director.createGaming();
        ComputerConfiguration customForHome = director.createCustom();

        System.out.println("BASIC: " + basic);
        System.out.println("GAMING: " + gaming);
        System.out.println("Custom: " + customForHome);
    }
}
