package builder;

public class ComputerConfigurationDirector {

    public ComputerConfiguration createGaming() {
        return new ComputerConfiguration.Builder(
                new CpuSpecs("AMD Ryzen 7 7600X3D", 8, 4.1),
                "B650 Wi-Fi board",
                "750W 80+ Gold certified",
                16,
                1024
        ).gpu("Nvidia GeForce RTX 5060 (8GB)")
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
                .operatingSystem("Windows 11 pro")
                .coolingType("Air Cooling")
                .build();
    }
}