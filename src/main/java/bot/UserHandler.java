package bot;

import java.util.HashMap;

import com.ullink.slack.simpleslackapi.SlackUser;

public class UserHandler {
	private SlackUser user;
	private int lunaState;
	private HashMap<LeanQuestion, String> ideaMap;

	public UserHandler(SlackUser user) {
		this.user = user;
		lunaState = -2;
		setIdeaMap(new HashMap<LeanQuestion, String>());
	}
	
	public String mapToString(){
		String out = "";
		for (LeanQuestion name: ideaMap.keySet()){

            String key =name.toString();
            String value = ideaMap.get(name).toString();  
            out += (key + " " + value + "\n");  
		}
		return out;

 
	}

	public HashMap<LeanQuestion, String> getIdeaMap() {
		return ideaMap;
	}

	public void setIdeaMap(HashMap<LeanQuestion, String> ideaMap) {
		this.ideaMap = ideaMap;
	}

	public SlackUser getUser() {
		return user;
	}

	public void setUser(SlackUser user) {
		this.user = user;
	}

	public int getLunaState() {
		return lunaState;
	}

	public void setLunaState(int lunaState) {
		this.lunaState = lunaState;
	}

}
