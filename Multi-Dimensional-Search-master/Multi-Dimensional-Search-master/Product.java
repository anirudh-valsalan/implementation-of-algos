import java.util.ArrayList;
import java.util.List;

/**
 * @author : Team G12
 *
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */
// Product Node which contains information about the product.
class Product {
	long id;//id of the element.
	List<Long> descList;//description list.
	double price;//price of the item.
    //constructor for the product class.
	Product(long id, List<Long> descList, double price) {
		this.id = id;
		this.descList = new ArrayList<Long>();
		for (long name : descList) {
			this.descList.add(name);
		}
		price = Math.floor((price+0.000001)*100)/100;
		this.price = price;
	}
}

