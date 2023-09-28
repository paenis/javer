public class IntArrayBag implements Cloneable {
    private int[] data;
    private int size;

    public IntArrayBag() throws OutOfMemoryError {
        data = new int[10];
        size = 0;
    }

    public IntArrayBag(int capacity) throws IllegalArgumentException, OutOfMemoryError {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be non-negative");
        data = new int[capacity];
        size = 0;
    }

    public static IntArrayBag union(/*NotNull*/IntArrayBag b1, /*NotNull*/IntArrayBag b2) {
        IntArrayBag result = new IntArrayBag(b1.size + b2.size);
        System.arraycopy(b1.data, 0, result.data, 0, b1.size);
        System.arraycopy(b2.data, 0, result.data, b1.size, b2.size);
        result.size = b1.size + b2.size;
        return result;
    }

    public static IntArrayBag intersection(IntArrayBag b1, IntArrayBag b2) { // incorrect, change later
        IntArrayBag result = new IntArrayBag();
        result.addAll(b1);
        // 2,2,2,3 intersect 2,2,3,4 -> 2,2,3

        // if there are too many of something in b1, remove it from result
        for (int i = 0; i < result.size; i++) {
            int removeTimes = result.countOccurrences(result.data[i]) - b2.countOccurrences(result.data[i]);
            int removeValue = result.data[i];
            System.out.printf("intersection: removeTimes = %d; removeValue = %d; index = %d%n", removeTimes, removeValue, i);
            for (int j = 0; j < removeTimes; j++) {
                System.out.printf("intersection: removing %d%n", removeValue);
                if (!result.remove(removeValue)) System.out.println("intersection: remove failed");
            }
            if (removeTimes > 0) i--; // since we removed an element, we need to check the same index again
        }
        return result;
    }

    public int getCapacity() {
        return data.length;
    }

    public int size() {
        return size;
    }

    public int countOccurrences(int value) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (data[i] == value) count++;
        }
        return count;
    }

    public void ensureCapacity(int capacity) {
        // System.out.printf("ensureCapacity(%d)%n", capacity);
        if (capacity > data.length) {
            System.out.printf("ensureCapacity: resizing %d -> %d%n", data.length, capacity);
            int[] newData = new int[capacity]; // alternatively double the size
            // int[] newData = Arrays.copyOf(data, capacity); // if using Arrays
            if (size >= 0) System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    public void trimToSize() {
        if (size < data.length) {
            System.out.printf("trimToSize: resizing %d -> %d%n", data.length, size);
            int[] newData = new int[size];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    public void add(int value) {
        if (size == data.length) ensureCapacity((size * 2) + 1);
        data[size] = value;
        size++;
    }

    public boolean remove(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                size--;
                data[i] = data[size];
                System.out.printf("remove: moved %d -> index %d over %d%n", data[i], i, value);
                return true;
            }
        }
        return false;
        // semantically equivalent:
        /*
        int i = 0;
        while (i < size && data[i] != value) i++;
        if (i == size) return false;
        size--;
        data[i] = data[size];
        return true;
        */
    }

    public void addAll(/*NotNull*/IntArrayBag other) {
        ensureCapacity(size + other.size);
        System.arraycopy(other.data, 0, data, size, other.size);
        size += other.size;
    }

    public void addAll(int... values) {
        ensureCapacity(size + values.length);
        for (int value : values) {
            this.add(value);
        }
    }

    public int removeAll(int value) {
        int count = this.countOccurrences(value);
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                data[i] = data[size - 1]; // overwrite with last element
                size--; // data past size is now garbage, no need to resize array
                i--;
            }
        }
        return count;
    }

    public void printUnsized() {
        StringBuilder sb = new StringBuilder("IntArrayBag(");
        for (int v : data) {
            sb.append(String.format("%d, ", v));
        }
        sb.replace(sb.length() - 2, sb.length() - 1, ")"); // replace `, ` with `)`

        System.out.println(sb);
    }

    @Override
    public String toString() {
        int[] sizedData = new int[size];
        System.arraycopy(data, 0, sizedData, 0, size);

        // simple string representation
        StringBuilder sb = new StringBuilder("IntArrayBag(");
        for (int v : sizedData) {
            sb.append(String.format("%d, ", v));
        }
        sb.replace(sb.length() - 2, sb.length(), ")"); // replace `, ` with `)`

        sb.append(String.format(" | size = %d; capacity = %d", size, data.length));
        return sb.toString();

        // return Arrays.toString(sizedData); // if using Arrays
    }

    @Override
    public IntArrayBag clone() {
        try {
            IntArrayBag clone = (IntArrayBag) super.clone();
            clone.data = data.clone(); // otherwise both objects will share the same array
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
