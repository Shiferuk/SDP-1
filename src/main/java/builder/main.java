package main.java.builder;

public class main {
    public static void main(String[] args) {
        ComputerConfigurationDirector director = new ComputerConfigurationDirector();

        ComputerConfiguration basic = director.createOffice("Student Basic");
        ComputerConfiguration gaming = director.createGaming("Student Gaming");
        ComputerConfiguration development = director.createCustom("Student Dev");

        System.out.println("BASIC: " + basic);
        System.out.println("GAMING: " + gaming);
        System.out.println("DEVELOPMENT: " + development);
        System.out.println("🍌");
    }
}
