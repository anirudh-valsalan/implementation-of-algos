import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
/**

 * @author : Team G12
 *
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */
public class MDS {
	
	TreeMap<Long, Product> idTree = new TreeMap<Long, Product>();//map to store the id product relation ship.Store id in sorted order.
	TreeMap<Long, DescNode> descMap = new TreeMap<Long, DescNode>();//map to store the desc and List of products for that desc relation.
	HashMap<Long, Product> idMap = new HashMap<Long, Product>();//hash map to store the id and price.Retrievel is faster when we use hashmap.
	/***
	 * insert 
	 * @param id
	 * @param price
	 * @param description
	 * @param size
	 * @return 1 if the item present in the list. else will return 0.
	 */
	public int insert(long id, double price, long[] description, int size) {
		boolean present = false;//to check whether the id already present or not.
	
		
		List<Long> descriptions = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			descriptions.add(description[i]);
		}
		price = Math.floor((price+0.000001)*100)/100;
		Product product = new Product(id, descriptions, price);
		// Get the oldNode from the tree
		Product oldProduct = idMap.get(id);
		boolean isContinue = false;
		if (oldProduct != null) {
			// New Entry
			present = true;
		}
		if (descriptions.isEmpty() && present) {
			//new description list for existing item.update the price and continue.
		
			
			oldProduct.price = price;

			product = oldProduct;

			isContinue = true;

		}
		if (!isContinue) {
			//if the item already present and desc list is not empty then
			if (present) {
                //retrieve the description list.
				List<Long> descriptionList = oldProduct.descList;
				
				for (Long name : descriptionList) {
				DescNode desc = descMap.get(name);
					desc.descList.remove(oldProduct);
				}
				
				oldProduct.price = price;
				oldProduct.descList = product.descList;
				product = oldProduct;//update the contents of old product and assign to product.

			} else {
				// Its a new product. Put the new product to IdTreeMap.
				idTree.put(id, product);
				idMap.put(id, product);
			}

			// if desc already present add it to the existing list or create a new map with desc as key.
			for (long desc : descriptions) {
				DescNode descList = descMap.get(desc);
				if (descList != null) {
					// description already present.
					List<Product> prodList = descList.descList;
					prodList.add(product);
				} else {
					//new description.
					descList = new DescNode(product);
					descMap.put(desc, descList);
				}
			}
		}
		
		if (!present) {
			//new
			return 1;
		} else {
			//already present.
			return 0;
		}
	}
 
	/**
	 * Find the price for given id.
	 * @param id
	 * @return the price.
	 */
	public double find(long id) {
		//check if the id already present or not.
	    Product productNode = idMap.get(id);
		if (productNode != null) {
			//Retrieve the product price.
			return productNode.price;
		}
		return 0;
	}
    /**
     * delete
     * @param id
     * @return the sum of all description for that id.
     */
	public long delete(long id) {
		long sum = 0;
		//retrieve the product based on the input id.
		Product product = idMap.get(id);
		if (product != null) {
			//retrieve the descriptions for the product.
			List<Long> descList = product.descList;
			for (long desc : descList) {
				//Retrieve the description node for that description.
				DescNode descNode = descMap.get(desc);
				//Retrieve the product list.
				List<Product> prodList = descNode.descList;
				prodList.remove(product);
				if (prodList.isEmpty())
					descMap.remove(desc);
				sum = sum + desc;

			}
			idTree.remove(id);
			idMap.remove(id);
          
		}

		return sum;
	}
  /**
   * findMaxPrice
   * @param des
   * @return the max price value.
   */
	public double findMaxPrice(long des) {
		//Retrieve the descNode
		DescNode desc = descMap.get(des);
		double max = 0;
		if (null != desc) {
			List<Product> prodList = desc.descList;
            //retrieve the all products which contain this description.
			if (!prodList.isEmpty()) {
				//compare the price with max and find the maximum
				max = Long.MIN_VALUE;
				for (Product p : prodList) {
					if (p.price > max)
						max = p.price;
				}
			}
		} else {
        
			max = 0;
		}
		return max;
	}
	/**
	 * findMinPrice
	 * @param des
	 * @return the minimum price.
	 */
	
	double findMinPrice(long des) {
		double min = 0;
		//Retrieve the descNode
		DescNode desc = descMap.get(des);
		if (null != desc) {
			//find all products which contains that description.
			List<Product> prodList = desc.descList;
			if (!prodList.isEmpty()) {
				//compare the price to find the minimum value.
				min = Long.MAX_VALUE;
				for (Product p : prodList) {
					if (p.price < min)
						min = p.price;
				}

			}
			//return the minimum value.
			return min;
		} else {

			return 0;
		}
	}
    /**
     * findPriceRange
     * @param des
     * @param low
     * @param high
     * @return the count of elements within this price range.
     */
	public int findPriceRange(long des, double low, double high) {
        //Retrieve the desc node from the desc map.
		DescNode desc = descMap.get(des);
		List<Product> descList = desc.descList;
		int count = 0;
		for (Product product : descList) {
			//if the price of the product is within this range increment the count.
			if (product.price >= low && product.price <= high) {
				count++;
			}
		}
		//return the count.
		return count;
	}
	/**
	 * priceHike
	 * @param minId
	 * @param maxId
	 * @param rate
	 * @return the sum of increase in price 
	 */

	public double priceHike(long minId, long maxId, double rate) {
		double oldPrice;//old price of the item.
		double newPrice;//new price of the item.
		double sum=0;//variable to keep track of sum.
		
		//retrieve the items from id map whose price is between the minId and maxId.
		
		SortedMap<Long, Product> sortedMap=idTree.subMap(minId, true, maxId, true);
		for (Product pro : sortedMap.values()) {
			oldPrice = pro.price;//retrieve the old price.
            double increase = rate * (pro.price / 100);//increase in the price.
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.DOWN);
			//total sum
			sum += increase;

			sum = Math.floor((sum + 0.000001) * 100) / 100;

         	newPrice = increase + oldPrice;//calculate the new price
			newPrice = Math.floor((newPrice + 0.000001) * 100) / 100;
			pro.price = newPrice;//update the product price with new price.
 
		

		}
		//return the sum.
		return sum;
	}

	/**
	 * range
	 * @param lowPrice
	 * @param highPrice
	 * @return the number of items whose price is between low price and high price.
	 */
	
	public int range(double lowPrice, double highPrice) {
		int count = 0;
			
		for(Product product :idMap.values()){
			if(product.price>=lowPrice && product.price<=highPrice){
				count++;
			}
		}
		
		return count;
	}
 
	/**
	 * SameSame
	 * find the count of descriptions of items whose length is greater than 7.
	 * @return count
	 */
	
	public int samesame() {
	
		int sum = 0;//to keep track of count
		List<Product> productList=new ArrayList<>();//product List which contain all the available products of length >
		//add products to product list.
		for (Entry<Long, Product> idList : idTree.entrySet()) {
			productList.add(idList.getValue());
		}
		Map<List<Long>, Integer> descCountMap = new HashMap<>();
		
		for (Product p : productList) {
			//check if size of the list is greater than 7.
			if (p.descList.size() > 7) {
				Integer freq = 1;
				Collections.sort(p.descList);//sort the elements in the list.
				//check if the desclist already present in the map.
				if (descCountMap.get(p.descList) == null) {
					//new description
					descCountMap.put(p.descList, freq);
				} else {
					//increment the count of the descriptions.
					Integer currentFreq = descCountMap.get(p.descList);
                    descCountMap.put(p.descList, currentFreq + 1);
				}
			}
		}
		//Iterate through the descMap and if the frequency of occurrence is greater than 1 add it to the final output.
		for (Integer count : descCountMap.values()) {
			if (count > 1) {
				sum += count;
			}
		}
		return sum;
	}	
}
