package ncu.im3069.demo.app;

import org.json.*;

import ncu.im3069.demo.util.MemberUpdate;
import ncu.im3069.demo.util.MemberGet;

public class Member {
	private int id;
    private String email;
    private String name;
    private String password;
    private int login_times;
    private String status;

    public Member() {
    }
    
    public Member(String email, String password, String name) {
    	this.email = email;
        this.password = password;
        this.name = name;
    }

    public Member(int id, String email, String password, String name) {
        this.id = id;
    	this.email = email;
        this.password = password;
        this.name = name;
        getLoginTimesStatus();
        checkStatus();
    }
    
    public Member(int id, String email, String password, String name, int login_times, String status) {
    	this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.login_times = login_times;
        this.status = status;
        checkStatus();
    }
    
    public int getID() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }
    
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
    
    public int getLoginTimes() {
        return this.login_times;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void addLoginCounter() {
    	MemberUpdate mu = new MemberUpdate();
    	int new_times = mu.updateLoginTimes(this);
    	this.login_times = new_times;
    	checkStatus();
    	
    	System.out.print("目前登入次數：" + Integer.toString(login_times));
    }
    
    public JSONObject getData() {
    	JSONObject jso = new JSONObject();
    	jso.put("id", this.id);
    	jso.put("name", this.name);
    	jso.put("email", this.email);
    	jso.put("password", this.password);
    	jso.put("login_times", this.login_times);
    	jso.put("status", this.status);
    	
    	return jso;
    }
    
    private void getLoginTimesStatus() {
    	MemberGet mg = new MemberGet();
    	JSONObject data = mg.getLoginTimesStatus(this);
    	this.login_times = data.getInt("login_times");
    	this.status = data.getString("status");
    }
    
    private void checkStatus() {
    	if(this.login_times > 15 && this.status.equals("junior")) {
    		MemberUpdate mu = new MemberUpdate();
    		mu.updateStatus(this, "senior");
    	}
    }
}