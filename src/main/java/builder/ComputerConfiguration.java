package builder;

public class ComputerConfiguration {
    private final CpuSpecs cpu;
    private final String motherBoard;
    private final String powerSupply;
    private final int ram;
    private final int storage;

    private final String gpu;
    private final boolean wifi;
    private final boolean bluetooth;
    private final boolean webcam;
    private final String operatingSystem;
    private final String coolingType;

    private ComputerConfiguration(Builder b) {
        this.cpu = b.cpu;
        this.motherBoard = b.motherBoard;
        this.powerSupply = b.powerSupply;
        this.ram = b.ram;
        this.storage = b.storage;
        this.gpu = b.gpu;
        this.wifi = b.wifi;
        this.bluetooth = b.bluetooth;
        this.webcam = b.webcam;
        this.operatingSystem = b.operatingSystem;
        this.coolingType = b.coolingType;
    }

    public CpuSpecs getCpu() { return cpu; }
    public String getMotherBoard() { return motherBoard; }
    public String getPowerSupply() { return powerSupply; }
    public int getRam() { return ram; }
    public int getStorage() { return storage; }

    @Override
    public String toString() {
        return "ComputerConfiguration{" +
                ", processor='" + cpu + '\'' +
                ", motherboard=" + motherBoard +
                ", powersupply=" + powerSupply +
                ", ramGb=" + ram +
                ", storage=" + storage +
                ", graphicsCard='" + gpu + '\'' +
                ", wifi=" + wifi +
                ", bluetooth=" + bluetooth +
                ", webcam=" + webcam +
                ", operatingSystem=" + operatingSystem +
                ", coolingType=" + coolingType + '}';
    }

    public static class Builder {
        private final CpuSpecs cpu;
        private final String motherBoard;
        private final String powerSupply;
        private final int ram;
        private final int storage;

        private String gpu = "NVIDIA GeForce RTX 5090";
        private boolean wifi = true;
        private boolean bluetooth = false;
        private boolean webcam = false;
        private String operatingSystem = "Windows 11 pro";
        private String coolingType = "Air Cooling";

        public Builder(CpuSpecs cpu, String motherBoard, String powerSupply, int ram, int storage) {
            this.cpu = cpu;
            this.motherBoard = motherBoard;
            this.powerSupply = powerSupply;
            this.ram = ram;
            this.storage = storage;
        }

        public Builder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }

        public Builder enableWifi() {
            this.wifi = true;
            return this;
        }

        public Builder enableBluetooth() {
            this.bluetooth = true;
            return this;
        }

        public Builder enableWebcam() {
            this.webcam = true;
            return this;
        }

        public Builder operatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public Builder coolingType(String coolingType) {
            this.coolingType = coolingType;
            return this;
        }

        public ComputerConfiguration build() {
            validate();
            return new ComputerConfiguration(this);
        }

        private void validate() {
            validateCPU();
            validateRAM();
            validateStorage();
            if ("Windows 11 pro".equalsIgnoreCase(operatingSystem) && ram < 8) {
                throw new IllegalStateException("Windows 11 requires at least 8GB RAM");
            }

            if (gpu != null && gpu.contains("RTX") && "No Cooling".equalsIgnoreCase(coolingType)) {
                throw new IllegalStateException("RTX Graphics Card requires an adequate Cooling Type");
            }
        }

        private void validateCPU() {
            if (cpu == null) {
                throw new IllegalArgumentException("cpuModel must not be blank");
            }
        }

        private void validateRAM() {
            if (ram < 0) {
                throw new IllegalArgumentException("RAM must not be less than zero");
            }
        }

        private void validateStorage() {
            if (storage < 0) {
                throw new IllegalArgumentException("Storage must not be less than zero");
            }
        }
    }
}


