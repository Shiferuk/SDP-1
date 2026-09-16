package builder;

public class CpuSpecs {
    private final String model;
    private final int cores;
    private final double clockSpeedGhz;

    public CpuSpecs(String model, int cores, double clockSpeedGhz) {
        if (model == null) {
            throw new IllegalArgumentException("CPU model cannot be blank");
        }
        this.model = model;
        this.cores = cores;
        this.clockSpeedGhz = clockSpeedGhz;
    }

    public String getModel() { return model; }
    public int getCores() { return cores; }
    public double getClockSpeedGhz() { return clockSpeedGhz; }

    @Override
    public String toString() {
        return model + " (" + cores + " cores @ " + clockSpeedGhz + "GHz)";
    }
}