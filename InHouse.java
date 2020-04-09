package Model;

public class InHouse extends Part {

    private int machineID;

    public InHouse () {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
    public int getMachineID() {
        return this.machineID;
    }
}
