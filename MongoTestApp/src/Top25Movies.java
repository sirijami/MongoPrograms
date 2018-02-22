import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Top25Movies {
	
	
	
	
	public static void main(String[] args){
		MongoClient mc = new MongoClient("localhost", 27017);
		MongoDatabase mdb = mc.getDatabase("MovieLensDataset");
		MongoCollection<Document> mcoll = mdb.getCollection("movieLensCollection");
		/*read the file and create a document and insert to the collection
		 *  */
		
		
		
	}

}
