import org.bson.Document;

import java.io.File;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/*Add external jar files */

public class TestApp1 {

	public static void main(String[] args) {
		/* Establish a connection */
		MongoClient mc = new MongoClient("localhost", 27017);

		/* Point to database */
		MongoDatabase db = mc.getDatabase("Jan20db");

		/* Point to collection */
		MongoCollection<Document> collection = db.getCollection("user");

		/* Point to document */
		Document doc = new Document();
		doc.append("first", "sirisha");
		doc.append("last", "epari");

		/* Insert document to collection */
		collection.insertOne(doc);

		System.out.println("Record inserted " + doc);
		
		
		Document d = collection.find().first();
		System.out.println(d);
		
		/* Import file, read line by line , use collection.insertMany() */
		

	}

}
