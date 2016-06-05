package bot;

import java.io.IOException;

import com.ullink.slack.simpleslackapi.SlackSession;

public class Luna {
	private SlackConnector connector;
	private SlackSession session;
	private CommunicationsService coms;

	public Luna() throws IOException {
		connector = new SlackConnector();
		connector.connect();
		this.session = connector.getSession();

//				SlackUser mart = this.session.findUserByEmail("martin.antonov.spasov@gmail.com");
//				this.session.sendMessageToUser(mart, "Hello scrub", null);

		coms = new CommunicationsService(this.session);
		coms.registeringAListener();

	}
}
