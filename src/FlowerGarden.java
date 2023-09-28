import java.util.ArrayList;

public class FlowerGarden {

    //create an arraylist instance variable
    private final ArrayList<String> flowers;

    //write a constructor
    public FlowerGarden() {
        flowers = new ArrayList<>();
    }

    //write add flower
    public void addFlower(String flower) {
        int idx = findFlower(flower);
        if (idx == -1) flowers.add(String.format("%02d %s", 1, flower));
        else {
            String oldFlower = getFlowerName(idx);
            int oldCount = getCount(flower);
            // move the flower to the last position
            flowers.remove(idx);
            flowers.add(String.format("%02d %s", oldCount + 1, oldFlower));
        }
    }

    //write get count
    public int getCount(String flower) {
        if (findFlower(flower) == -1) return 0;
        return Integer.parseInt(flowers.get(findFlower(flower)).substring(0, 2));
    }

    //write get flower name
    public String getFlowerName(int index) {
        if (!inRange(index)) return "out of range";
        return flowers.get(index).substring(3);
    }

    //write in range
    private boolean inRange(int index) {
        return index >= 0 && index < flowers.size();
    }

    //write find flower
    public int findFlower(String flower) {
        for (int i = 0; i < flowers.size(); i++) {
            if (getFlowerName(i).equals(flower)) return i;
        }
        return -1;
    }

    //write max flower
    public String maxFlower() {
        int max = 0;
        for (int i = 0; i < flowers.size(); i++) {
            if (getCount(getFlowerName(i)) > getCount(getFlowerName(max))) max = i;
        }
        return flowers.get(max);
    }

    //write a toString
    public String toString() {
        return flowers.toString();
    }
}
