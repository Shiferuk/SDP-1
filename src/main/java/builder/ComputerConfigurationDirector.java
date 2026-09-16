package builder;

public class ComputerConfigurationDirector {

    public ComputerConfiguration createGaming() {
        return new ComputerConfiguration.Builder(
                new CpuSpecs("AMD Ryzen 9 9950X", 16, 5.7),
                "X670E",
                "1000W 80+ Gold certified",
                64,
                2048
        ).gpu("Nvidia GeForce RTX 5090")
                .enableWifi()
                .enableBluetooth()
                .enableWebcam()
                .operatingSystem("Windows 11 pro")
                .coolingType("Liquid Cooling")
                .build();
    }

    public ComputerConfiguration createOffice() {
        return new ComputerConfiguration.Builder(
                new CpuSpecs("Intel Core i3-13100", 4, 3.4),
                "B650 Wi-Fi board",
                "450W PSU",
                8,
                256
        ).gpu("Integrated Intel UHD")
                .enableWifi()
                .enableBluetooth()
                .enableWebcam()
                .operatingSystem("Windows 10/11")
                .coolingType("Air Cooling")
                .build();
    }

    public ComputerConfiguration createCustom() {
        return new ComputerConfiguration.Builder(
                new CpuSpecs("Intel Core i5-13600k", 6, 3.5),
                "ASRock H610M-HDV ",
                "Corsair CX450M",
                16,
                1024
        ).gpu("Nvidia GTX 1650")
                .enableWifi()
                .enableBluetooth()
                .enableWebcam()
                .operatingSystem("Linux Ubuntu")
                .coolingType("Air Cooling")
                .build();
    }
}