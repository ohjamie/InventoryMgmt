package Model;

public abstract class Part {

    protected int partID;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;

    // CONSTRUCTOR: COMPLETE
    public Part() {
        setPartID(partID);
        setName(name);
        setStock(stock);
        setPrice(price);
        setMin(min);
        setMax(max);
    }

    // SETTERS: COMPLETE

    public void setPartID(int partID) { this.partID = partID; }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }

    // GETTERS: COMPLETE

    public int getPartID() { return this.partID; }
    public String getName() { return this.name; }
    public double getPrice() {
        return this.price;
    }
    public int getStock(){
        return this.stock;
    }
    public int getMin(){
        return this.min;
    }
    public int getMax(){
        return this.max;
    }

}
