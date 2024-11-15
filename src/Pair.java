class Pair implements Comparable<Pair> {
    int weight;
    Grid grid;

    public Pair(int weight, Grid grid) {
        this.weight = weight;
        this.grid = grid;
    }

    @Override
    public int compareTo(Pair other) {
        return Integer.compare(this.weight, other.weight);
    }
}
