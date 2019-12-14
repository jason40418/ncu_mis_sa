package ncu.im3069.Group16.app;

import org.json.*;
import java.util.Calendar;

public class Teacher {
	/** id，老師會員編號 */
	private int id;
    
	/** name，老師會員姓名 */
	private String name;
    
	/** email，老師會員電子郵件信箱 */
	private String email;
	
	/** password，老師會員密碼 */
	private String password;
	
	/** cellphone，家長會員手機*/
    private String cellphone;
    
    /** gender，家長會元性別*/
    private int gender;
	
	/** ph，ParentHelper之物件與Parent相關之資料庫方法（Sigleton） */
    private TeacherHelper th =  TeacherHelper.getHelper();
	
    /** constructor_1 建構子1 (doPost()會用到)
     * 實例化（Instantiates）一個新的（new）Teacher物件
     * 採用多載（overload）方法進行，此建構子用於建立老師會員資料時，產生一名新的老師會員
     *
     * 
     * @param name 老師會員姓名
     * @param email 老師會員電子信箱
     * @param password 老師會員密碼
     * @param cellphone 老師會員電話
     * @param gender 老師會員性別
     */
    public Teacher(String name, String email, String password, String cellphone, int gender) {
    	this.name = name;
		this.email = email;
		this.password = password;
		this.cellphone = cellphone;
		this.gender = gender;
        update();
    }
    
    /** constructor_2 建構子2 (doGet()會用到)
     * 實例化（Instantiates）一個新的（new）Teacher物件
     * 採用多載（overload）方法進行，此建構子用於"查詢"會員資料時，將每一筆資料新增為一個會員物件
     *
     * @param id 老師會員編號
     * @param email 老師會員電子信箱
     * @param password 老師會員密碼
     * @param name 老師會員姓名
     * @param cellphone 老師會員電話
     * @param gender 老師會員性別
     */
	public Teacher(int id, String name, String email, String password, String cellphone, int gender) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.cellphone = cellphone;
		this.gender = gender;
	}
	
	/** constructor_3 建構子3 (doPut()會用到)
     * 實例化（Instantiates）一個新的（new）Teacher物件
     * 採用多載（overload）方法進行，此建構子用於"更新"老師會員資料時，產生一名老師會員
     * 
     * @param id 老師會員編號
     * @param email 老師會員電子信箱
     * @param password 老師會員密碼
     * @param cellphone 老師會員姓手機
     */
	public Teacher(int id, String email, String password, String cellphone) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.cellphone = cellphone;
	}
	
	/**
     * 取得老師會員之編號
     *
     * @return the id 回傳老師會員編號
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * 取得老師會員之姓名
     *
     * @return the name 回傳老師會員姓名
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * 取得老師會員之電子郵件信箱
     *
     * @return the email 回傳老師會員電子郵件信箱
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * 取得老師會員之密碼
     *
     * @return the password 回傳老師會員密碼
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * 取得老師會員之電話
     *
     * @return the cellphone 回傳老師會員電話
     */
    public String getCellphone() {
        return this.cellphone;
    }
    
    /**
     * 取得老師會員的性別
     * 
     * @return the gender 回傳老師會員性別
     */
    public int getGender() {
    	return this.gender;
    }
    
    /**
     * 更新老師會員資料
     *
     * @return the JSON object 回傳SQL更新之結果與相關封裝之資料
     */
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 檢查該名會員是否已經在資料庫 */
        if(this.id != 0) {
        	/** 若有則將目前更新後之資料更新至資料庫中 */
        	//th.updateLoginTimes(this);
        	
        	/** 透過ParentHelper物件，更新目前之老師會員資料置資料庫中
        	 * 傳入此Parent物件並回傳一個JSONObject物件
        	 */
        	data = th.update(this);
        }
        
        return data;
    }
    
	/**
	* 取得該名老師會員所有資料
	*
	* @return the data 取得該名老師會員之所有資料並封裝於JSONObject物件內
	*/
	public JSONObject getData() {
		/** 透過JSONObject將該名老師會員所需之資料全部進行封裝 */ 
		JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("email", getEmail());
        jso.put("password", getPassword());
        jso.put("cellphone", getCellphone());
        jso.put("gender", getGender());
        //jso.put("login_times", getLoginTimes());
        //jso.put("status", getStatus());
        
        return jso;
    }
}