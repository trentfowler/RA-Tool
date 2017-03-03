import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Obj> store;
	
	public Store() {
		store = new ArrayList<Obj>();
	}
		
	/** 
	 * Locate an object based on SR.
	 * 
	 * @param sr	Service Request to locate
	 * @return		The matching object. Returns null if not found.
	 */
	public Obj get(String sr) {
		for (Obj obj : this.store) {
			if (obj.sr.trim().equalsIgnoreCase(sr.trim())) {
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * Number of elements (objs) in store.
	 * @return	Total number of elements
	 */
	public int size() {
		return this.store.size();
	}
	
	/**
	 * Return the store. 
	 * 
	 * @return ArrayList of objs
	 */
	public ArrayList<Obj> get() {
		return this.store;
	}
	
	/**
	 * Add obj to store.
	 * 
	 * @param o	Obj
	 */
	public void add(Obj o) {
		for (int i = 0; i < this.store.size(); i++) {
			Obj obj = this.store.get(i);
			if (obj.sr.trim().equalsIgnoreCase(o.sr.trim())) {
				this.store.set(i, o);
				return;
			}
		}
		this.store.add(o);
	}
}

class Obj implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public boolean called_checked = false;
	public boolean system_model_checked = false;
	public boolean sr_checked = false;
	public boolean issue_checked = false;
	public boolean agent_desc_checked = false; 
	public boolean customer_desc_checked = false; 
	public boolean recent_history_checked = false; 
	public boolean dc_sess_checked = false; 
	public boolean parent_sr_checked = false; 
	public boolean fusion_checked = false; 
	public boolean entitlement_checked = false; 
	public boolean invoice_checked = false; 
	public boolean submitter_checked = false; 
	public boolean financial_impact_checked = false; 
	public boolean clca_checked = false; 
	public boolean os_checked = false; 
	public boolean oracle_checked = false; 
	public boolean sit_checked = false; 
	public boolean peripherals_checked = false; 
	public boolean raid_checked = false; 
	public boolean bios_checked = false; 
	public boolean hcrt_next_actions_checked = false; 
	public boolean customer_next_actions_checked = false; 
	public String called = "";
	public String timestamp = "";
	public int selected_hashtag = 0; 
	public String system_model = "";
	public String sr = "";
	public String issue = "";
	public String agent_desc = "";
	public String customer_desc = "";
	public String recent_history = "";
	public String dc_sess = "";
	public String parent_sr = "";
	public String fusion = "";
	public int selected_entitlement = 0;
	public int selected_architecture = 0;
	public String invoice = "";
	public String submitter = "";
	public String financial_impact = "";
	public String clca = "";
	public int selected_os = 0;
	public String oracle = "";
	public String sit = "";
	public String peripherals = "";
	public String raid = "";
	public String bios = "";
	public String steps_taken = "";
	public String hcrt_next_actions = "";
	public String customer_next_actions = "";
	public String notes = "";
	public String current_call = "";
	public String current_phone = "";
	public String current_timestamp = "";
	public boolean current_call_checked = false;
	
	public Obj() {
		
	}
}