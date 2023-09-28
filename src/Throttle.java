public class Throttle {
    int maxFlow;
    int currentFlow;

    public Throttle(int maxFlow) throws IllegalArgumentException {
        if (maxFlow < 0) throw new IllegalArgumentException("maxFlow <= 0");
        this.maxFlow = maxFlow;
        this.currentFlow = 0;
    }

    public void shutOff() {
        currentFlow = 0;
    }

    public boolean isOn() {
        return currentFlow > 0; // or != 0
    }

    public void shift(int amount) {
        currentFlow += amount;
        if (currentFlow < 0) currentFlow = 0; // or throw exception
        if (currentFlow > maxFlow) currentFlow = maxFlow; // see above
    }

    public double/*?*/ fuelFlow() {
        return currentFlow / (double) maxFlow;
    }

    public int getCurrentFlow() {
        return currentFlow;
    }

    /*public void setCurrentFlow(int currentFlow) {
        this.currentFlow = currentFlow;
    }*/

    public int getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(int maxFlow) {
        this.maxFlow = maxFlow;
    }

    @Override
    public String toString() {
        return String.format("Throttle(maxFlow=%d, currentFlow=%d)", maxFlow, currentFlow);
    }}
