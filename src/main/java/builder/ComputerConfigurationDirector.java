package main.java.builder;

public class ComputerConfigurationDirector {
    public ComputerConfiguration createGaming(String model) {
        return new ComputerConfiguration.Builder(
                "AMD Ryzen 7 7600X3D",
                "B650 Wi-Fi board",
                "750W 80+ Gold certified",
                16,
                1024
        ).GPU("Nvidia GeForce RTX 5060 (8GB)")
                .enableWifi()
                .enableBluetooth()
                .enableWebcam()
                .OperatingSystem("Windows 11 pro")
                .CoolingType("Standard mid-tower case with an air").build();
    }

    public ComputerConfiguration createOffice(String model) {
        return new ComputerConfiguration.Builder(
                "Intel Core i3",
                "B650 Wi-Fi board",
                "750W 80+ Gold certified",
                8,
                256
        ).GPU("Integrated Intel UHD")
                .enableWifi()
                .enableBluetooth()
                .enableWebcam()
                .OperatingSystem("Windows 11")
                .CoolingType("Standard mid-tower case with an air").build();
    }

    public ComputerConfiguration createCustom(String model) {
        return new ComputerConfiguration.Builder(
                "AMD Ryzen 7 7600X3D",
                "B650 Wi-Fi board",
                "750W 80+ Gold certified",
                16,
                1024
        ).GPU("Nvidia GeForce RTX 5060 (8GB)")
                .enableWifi()
                .enableBluetooth()
                .enableWebcam()
                .OperatingSystem("Windows 11 pro")
                .CoolingType("Standard mid-tower case with an air").build();
    }
}

