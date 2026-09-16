package builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComputerConfigurationTest {
    @Test
    @DisplayName("Valid scenario 1: Build basic Office PC")
    void testValidOfficeConfig() {
        CpuSpecs cpu = new CpuSpecs("Intel Core i3-13100", 4, 3.4);
        ComputerConfiguration config = new ComputerConfiguration.Builder(cpu, "B650 Board", "450W PSU", 8, 256)
                .gpu("Integrated Intel UHD")
                .coolingType("Air Cooling")
                .operatingSystem("Windows 11 pro")
                .build();

        assertNotNull(config);
        assertEquals("Intel Core i3-13100", config.getCpu().getModel());
        assertEquals(8, config.getRam());

        System.out.println("Successfully built configuration: " + config);
    }

    @Test
    @DisplayName("Valid scenario 2: Build Gaming PC with Liquid Cooling")
    void testValidGamingConfig() {
        CpuSpecs cpu = new CpuSpecs("AMD Ryzen 7 7600X3D", 8, 4.1);
        ComputerConfiguration config = new ComputerConfiguration.Builder(cpu, "B650 Board", "750W PSU", 16, 1024)
                .gpu("Nvidia GeForce RTX 5060")
                .coolingType("Liquid Cooling")
                .operatingSystem("Windows 11 pro")
                .build();

        assertNotNull(config);
        assertEquals(16, config.getRam());
        assertEquals("Liquid Cooling", config.getCoolingType());
    }

    @Test
    @DisplayName("Valid scenario 3: Build Custom Workstation")
    void testValidCustomConfig() {
        CpuSpecs cpu = new CpuSpecs("Intel Core i9-14900K", 24, 3.2);
        ComputerConfiguration config = new ComputerConfiguration.Builder(cpu, "Z790 Board", "1000W PSU", 32, 2048)
                .gpu("Nvidia GeForce RTX 4090")
                .coolingType("Liquid Cooling")
                .operatingSystem("Windows 11 pro")
                .enableWifi()
                .enableBluetooth()
                .build();

        assertNotNull(config);
        assertTrue(config.isWifi());
        assertTrue(config.isBluetooth());
    }

    @Test
    @DisplayName("Invalid scenario 1: Null CPU specs should throw IllegalArgumentException")
    void testInvalidCpuNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerConfiguration.Builder(null, "B650 Board", "500W PSU", 16, 512).build()
        );
        assertTrue(exception.getMessage().contains("CPU must not be null"));
    }

    @Test
    @DisplayName("Invalid scenario 2: Zero RAM should throw IllegalArgumentException")
    void testInvalidRamZero() {
        CpuSpecs cpu = new CpuSpecs("Intel i5", 6, 2.5);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerConfiguration.Builder(cpu, "B650 Board", "500W PSU", 0, 512).build()
        );
        assertTrue(exception.getMessage().contains("RAM must be greater than zero"));
    }

    @Test
    @DisplayName("Invalid scenario 3: Negative storage should throw IllegalArgumentException")
    void testInvalidStorageNegative() {
        CpuSpecs cpu = new CpuSpecs("Intel i5", 6, 2.5);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerConfiguration.Builder(cpu, "B650 Board", "500W PSU", 16, -100).build()
        );
        assertTrue(exception.getMessage().contains("Storage must be greater than zero"));
    }

    @Test
    @DisplayName("Boundary case 1: Minimum valid RAM (8GB) for Windows 11")
    void testBoundaryMinRamValid() {
        CpuSpecs cpu = new CpuSpecs("Intel i3", 4, 3.0);
        ComputerConfiguration config = new ComputerConfiguration.Builder(cpu, "B650", "400W", 8, 256)
                .operatingSystem("Windows 11 pro")
                .gpu("Integrated")
                .coolingType("Air Cooling")
                .build();

        assertEquals(8, config.getRam());
    }

    @Test
    @DisplayName("Boundary case 2: Minimum valid Storage (1GB)")
    void testBoundaryMinStorageValid() {
        CpuSpecs cpu = new CpuSpecs("Intel i3", 4, 3.0);
        ComputerConfiguration config = new ComputerConfiguration.Builder(cpu, "B650", "400W", 8, 1)
                .operatingSystem("Linux")
                .gpu("Integrated")
                .coolingType("Air Cooling")
                .build();

        assertEquals(1, config.getStorage());
    }

    @Test
    @DisplayName("Constraint test: Windows 11 with RAM < 8GB should throw IllegalStateException")
    void testCrossFieldValidationWindows11RamFailure() {
        CpuSpecs cpu = new CpuSpecs("Intel i3", 4, 3.0);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new ComputerConfiguration.Builder(cpu, "B650", "400W", 4, 256)
                        .operatingSystem("Windows 11 pro")
                        .gpu("Integrated")
                        .build()
        );
        assertTrue(exception.getMessage().contains("Windows 11 requires at least 8GB RAM"));
    }

    @Test
    @DisplayName("Independence test: Modifying builder after build() does not change already created product")
    void testBuilderReuseAndProductIndependence() {
        CpuSpecs cpu = new CpuSpecs("Intel i5", 6, 2.5);
        ComputerConfiguration.Builder builder = new ComputerConfiguration.Builder(cpu, "B650", "500W", 16, 512)
                .coolingType("Air Cooling")
                .operatingSystem("Linux");

        ComputerConfiguration firstConfig = builder.build();

        builder.gpu("Nvidia RTX 4090")
                .coolingType("Liquid Cooling")
                .enableWifi();

        ComputerConfiguration secondConfig = builder.build();

        assertNotEquals(firstConfig.getGpu(), secondConfig.getGpu());
        assertEquals("Air Cooling", firstConfig.getCoolingType());
        assertEquals("Liquid Cooling", secondConfig.getCoolingType());
    }
}