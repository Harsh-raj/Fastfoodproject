package practice;

public class Orders {
	
	public String food_item;
    public int price;

    public Orders(String food_item, int price) {
    	this.food_item = food_item;
    	this.price = price;
    }

    public String getFood_item() {
    	return food_item;
    }

    public int getPrice() {
    	return price;
    }
}
