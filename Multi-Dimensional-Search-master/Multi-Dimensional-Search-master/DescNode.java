import java.util.LinkedList;
import java.util.List;

/**
 * @author : Team G12
 *
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */

// DescNode which stores the product details.
class DescNode {
	List<Product> descList = null;

	DescNode(Product productNode) {
		if (descList == null) {
			descList = new LinkedList<Product>();
			descList.add(productNode);

		} else {
			descList.add(productNode);
		}
	}
}