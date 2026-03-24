public class Gadget 
{
    private String model;
    private double price;
    private double weight;// Changed from int to double
    private String size;

    // Constructor
    public Gadget(String model, double price, double weight, String size)
    {
        this.model = model;
        this.price = price;
        this.weight = weight;  // now double
        this.size = size;
    } 
    
    // Accessor for model
    public String getModel() 
    {
       return model; 
    }
    // Display method
    public void display() {
        System.out.println("Model: " + model);
        System.out.println("Price: £" + price);
        System.out.println("Weight: " + weight + "g");
        System.out.println("Size: " + size);
    }
}