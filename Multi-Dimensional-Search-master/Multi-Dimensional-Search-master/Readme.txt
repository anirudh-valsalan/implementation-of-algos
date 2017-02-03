# Multi-Dimensional-Search
Designed and developed a multi-dimensional search library in Java to make the efficient search based on different attributes of a product.
The data was organized using appropriate data structures that are best suited for various different operations  
Insert(id,price,description): insert a new item. If an entry with the same id already exists, its description and price are replaced by the new values. If description is empty, then just the price is updated. Returns 1 if the item is new, and 0 otherwise. 
Find(id): return price of item with given id (or 0, if not found).
Delete(id): delete item from storage. Returns the sum of the long ints that are in the description of the item deleted (or 0, if such an id did not exist).
FindMinPrice(n): given a long int n, find items whose description contains n (exact match with one of the long ints in the item's description), and return lowest price of those items. 
FindMaxPrice(n): given a long int n, find items whose description contains n, and return highest price of those items.
FindPriceRange(n,low,high): given a long int n, find the number of items whose description contains n, and their prices fall within the given range, [low, high]. PriceHike(l,h,r): increase the price of every product, whose id is in the range [l,h], by r% Discard any fractional pennies in the new prices of items. Returns the sum of the net increases of the prices. 
Range(low, high): number of items whose price is at least "low" and at most "high". 
SameSame(): Find the number of items that satisfy all of the following conditions: The description of the item contains 8 or more numbers, and, The description of the item contains exactly the same set of numbers as another item.
