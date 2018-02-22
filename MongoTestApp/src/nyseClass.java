//import org.bson.Document;
//
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//
//public class nyseClass {
//
//	public static void main(String[] args) {
//		MongoClient mc = new MongoClient("localhost", 27017);
//		MongoDatabase mdb = mc.getDatabase("testDatabase");
//		MongoCollection<Document> mcoll = mdb.getCollection("testCollection");
//		Document d = mcoll.find().first();
//		System.out.println(d);
//		mapperFunction(mcoll);
//		      
//	}
//	public static void mapperFunction(mcoll){
//		emit(this.symbol, this.price);		
//	}
//	public static void reduceFunction(){
//		
//	}
//
//}
