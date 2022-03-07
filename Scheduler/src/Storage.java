import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Storage {
	
	private Cache cache;
	private boolean ready = false;
	private File storage;
	

	public Storage() {
		this.cache = new Cache();
		this.ready = false;
		this.storage = new File(Constants.getFilePath());
	}
	
	protected ArrayList<CacheObject> getItems() {
		return this.cache.readCache();
	}
	
	protected void addItem(CacheObject o) {
		this.cache.storeToCache(o);
		this.storeCache();
	}
	
	protected void initStorage() {
		try {
			System.out.println("Initing store to: " + Constants.getFilePath());
			if(!this.storage.exists() && !this.storage.isDirectory()) {
				// Store exits
				System.out.println("No store present: Creating store");
				this.storage.createNewFile();
				System.out.println("File" + this.storage.getAbsolutePath());
				FileWriter f = new FileWriter(this.storage, false);
				f.write("");
				f.close();
				this.ready = true;
			} else {
				this.ready = true;
				this.readStoreToCache();
			}
			this.ready = true;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void storeCache() {
		try {
			this.validateReady();
			this.emptyStorage();
			System.out.println("Storing cache");
			FileWriter f = new FileWriter(this.storage);
			for(CacheObject c : this.cache.readCache()) {
				f.write(JSONHelper.hashMapToJson(c) + ",\n");
			}
			f.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void readStoreToCache() {
		try {
			this.validateReady();
			Scanner reader = new Scanner(this.storage);
			while(reader.hasNextLine()) {
				String rowData = reader.nextLine();
				this.cache.storeToCache(this.hashMapToCacheObject(JSONHelper.jsonToHashMap(rowData)));
			}
			reader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void validateReady() throws Exception {
		if(!this.ready) {
			throw new Exception("Store not ready");
		}
	}
	
	protected void emptyStorage () throws IOException {
		FileWriter f = new FileWriter(this.storage, false);
		f.write("");
		f.close();
	}
	
	protected void emptyCache() {
		this.cache.empty();
	}
	
	private CacheObject hashMapToCacheObject(HashMap<String,String> hm) {
		CacheObject co = new CacheObject();
		ArrayList<String> keys = new ArrayList<String>(hm.keySet());
		for(String key : keys) {
			co.put(key, hm.get(key));
		}
		return co;
	}

}

class JSONHelper {
	private static String Q = "\"";
	public static HashMap<String,String> jsonToHashMap(String json){
		HashMap<String,String> result = new HashMap<String,String>();
		String[] sources = json.replaceAll("\\{|\\}\\,","").split(",");
		for(String source : sources) {
			String[] keyAndValue = source.split("\":\"");
			if(keyAndValue.length != 2) {
				continue;
			}
			result.put(keyAndValue[0].replaceAll("\"",""),keyAndValue[1].replaceAll("\"",""));
		}
		return result;
	}
	public static String hashMapToJson (HashMap<String,String> o) {
		List<String> items = new ArrayList<String>();
		for(String key : o.keySet()) {
			items.add(getJSONKeyValuePair(key, o.get(key)));
		}
		return "{" + String.join(",", items) + "}";
	}
	private static String getJSONKeyValuePair (String key, String value) {
		return Q + key + Q + ":" + Q + value + Q;
	}
}


class Cache {
	protected ArrayList<CacheObject> cache;
	
	public Cache() {
		this.cache = new ArrayList<CacheObject>();
	}
	
	public ArrayList<CacheObject> readCache() {
		return this.cache;
	}
	
	public void storeToCache (CacheObject o) {
		this.cache.add(o);
	}
	
	public void empty() {
		this.cache.clear();
	}
}

class CacheObject extends HashMap<String,String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
