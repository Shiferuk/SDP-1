package main.java.builder;

public class ComputerConfiguration {
    private final String CPU;
    private final String MotherBoard;
    private final String PowerSupply;
    private final int RAM;
    private final int storage;

    private final String GPU;
    private final boolean wifi;
    private final boolean bluetooth;
    private final boolean webcam;
    private final String OperatingSystem;
    private final String CoolingType;

    private ComputerConfiguration(Builder b) {
        this.CPU = b.CPU;
        this.MotherBoard = b.MotherBoard;
        this.PowerSupply = b.PowerSupply;
        this.RAM = b.RAM;
        this.storage = b.storage;
        this.GPU = b.GPU;
        this.wifi = b.wifi;
        this.bluetooth = b.bluetooth;
        this.webcam = b.webcam;
        this.OperatingSystem = b.OperatingSystem;
        this.CoolingType = b.CoolingType;
    }

    public String getCPU() { return CPU; }
    public String getMotherBoard() { return MotherBoard; }
    public String getPowerSupply() { return PowerSupply; }
    public int getRAM() { return RAM; }
    public int getStorage() { return storage; }

    public static class Builder {
        private final String CPU;
        private final String MotherBoard;
        private final String PowerSupply;
        private final int RAM;
        private final int storage;

        private String GPU = "NVIDIA GeForce RTX 5090";
        private boolean wifi = true;
        private boolean bluetooth = false;
        private boolean webcam = false;
        private String OperatingSystem = "Windows 11 pro";
        private String CoolingType = "Air Cooling";

        @Override
        public String toString() {
            return "ComputerConfiguration{" +
                    ", processor='" + CPU + '\'' +
                    ", motherboard=" + MotherBoard +
                    ", powersupply=" + PowerSupply +
                    ", ramGb=" + RAM +
                    ", storage=" + storage +
                    ", graphicsCard='" + GPU + '\'' +
                    ", wifi=" + wifi +
                    ", bluetooth=" + bluetooth +
                    ", webcam=" + webcam +
                    ", operatingSystem=" + OperatingSystem +
                    ", coolingType=" + CoolingType + '}';
        }

        public Builder(String CPU, String MotherBoard, String PowerSupply, int RAM, int storage) {
            this.CPU = CPU;
            this.MotherBoard = MotherBoard;
            this.PowerSupply = PowerSupply;
            this.RAM = RAM;
            this.storage = storage;
        }

        public Builder GPU(String GPU) {
            this.GPU = GPU;
            return this;
        }

        public Builder enableWifi() {
            this.wifi = true;
            return this;
        }

        public Builder enableBluetooth() {
            this.bluetooth = false;
            return this;
        }

        public Builder enableWebcam() {
            this.webcam = false;
            return this;
        }

        public Builder OperatingSystem(String OperatingSystem) {
            this.OperatingSystem = OperatingSystem;
            return this;
        }

        public Builder CoolingType(String CoolingType) {
            this.CoolingType = CoolingType;
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
        }

        private void validateCPU() {
            if (CPU == null || CPU.isBlank()) {
                throw new IllegalArgumentException("cpuModel must not be blank");
            }
        }

        private void validateRAM() {
            if (RAM < 0) {
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


