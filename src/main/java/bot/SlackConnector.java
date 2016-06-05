package bot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import java.io.IOException;

/**
 * This sample code is creating a Slack session and is connecting to slack. To get some more details on
 * how to get a token, please have a look here : https://api.slack.com/bot-users
 */
public class SlackConnector{
    
	private SlackSession session;
	
	public void connect() throws IOException{
        this.session = SlackSessionFactory.createWebSocketSlackSession("");
        this.session.connect();

    }

	public SlackSession getSession() {
		return this.session;
	}
	
	
}
