package bot;

import java.util.concurrent.CopyOnWriteArrayList;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

public class CommDock {
	private SlackSession session;
	private CopyOnWriteArrayList<UserHandler> userList;

	public CommDock(SlackSession session) {
		this.session = session;
		userList = new CopyOnWriteArrayList<UserHandler>();
	}

	public void handleEvent(SlackMessagePosted event) {
		SlackChannel channel = event.getChannel();
		String msg = event.getMessageContent();
		SlackUser user = event.getSender();
		UserHandler userHandler = null;

		boolean isNewUser = true;
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUser().equals(user)) {
				userHandler = userList.get(i);
				isNewUser = false;
			}
		}
		if (isNewUser) {
			userHandler = new UserHandler(user);
			userList.add(userHandler);
		}
		if (userHandler.getLunaState() != -2 && userHandler.getLunaState() != -1 && msg.toLowerCase().equals("stop")) {
			session.sendMessage(channel, ":frowning: Hit me up when you are ready for innovation! :frowning:");
			userHandler.setLunaState(-2);
		}

		switch (userHandler.getLunaState()) {

			case -2:
				if (msg.toLowerCase().contains("luna") || msg.toLowerCase().contains("hi") || msg.toLowerCase().contains("hello")) {
					session.sendMessage(channel, "Yes sweety, Can I help you with an idea you have?");
					userHandler.setLunaState(-1);
				}
				break;
			case -1:
				if (msg.toLowerCase().charAt(0) == 'y') {
					session.sendMessage(channel, "Alright then, ill you a few questions that might help you makes sense of your idea!");
					session.sendMessage(channel, "Hit me up when you're ready! :smile: ");
					userHandler.setLunaState(0);
				} else if (msg.toLowerCase().charAt(0) == 'n') {
					session.sendMessage(channel, ":frowning: Hit me up when you are ready for innovation! :frowning:");
					userHandler.setLunaState(-2);
				} else {
					session.sendMessage(channel, "Sorry I didn't understand you");
				}
				break;
			case 0:
				session.sendMessage(channel, "Ok, " + user.getRealName() + " :grin: If at any point you wanna stop, just tell me!");
				session.sendMessage(channel, "Let's Being :grin: If at any point you wanna stop, just tell me! Remember to keep your answers as short and clear as possible!");
				session.sendMessage(channel, "What is the tagline of your idea? Example: \"A bot that helps you order lunch\"");
				userHandler.setLunaState(1);
				break;
			case 1:
				userHandler.getIdeaMap().put(LeanQuestion.TAGLINE , msg);
				session.sendMessage(channel, "What is the problem that you have encountered?");
				userHandler.setLunaState(2);
				break;
	
			case 2:
				userHandler.getIdeaMap().put(LeanQuestion.PROBLEM , msg);
				session.sendMessage(channel, "Are there any existing solutions to the issue? If so, what are they?");
				userHandler.setLunaState(3);
				break;
				
			case 3:
				userHandler.getIdeaMap().put(LeanQuestion.ALTERNATIVES , msg);
				session.sendMessage(channel, "I am sure you have a better solution! What is it?");
				userHandler.setLunaState(4);
				break;
				
			case 4:
				userHandler.getIdeaMap().put(LeanQuestion.SOLUTION , msg);
				session.sendMessage(channel, "What are the advantages your solutions has over the alternatives you just told me?");
				userHandler.setLunaState(5);
				break;
				
			case 5:
				userHandler.getIdeaMap().put(LeanQuestion.UNFAIR_ADVANTAGE , msg);
				session.sendMessage(channel, "What exactly makes your solution so unique?");
				userHandler.setLunaState(6);
				break;	
				
			case 6:
				userHandler.getIdeaMap().put(LeanQuestion.UNIQUE_VALUE , msg);
				session.sendMessage(channel, "Wow, that looks epic :heart_eyes: ");
				session.sendMessage(channel, "Imagine we realize this idea! It would be awesome! Who whould benifit the most from it?");
				userHandler.setLunaState(7);
				break;	
			case 7:
				userHandler.getIdeaMap().put(LeanQuestion.CUSTOMER_SEGMENTS , msg);
				session.sendMessage(channel, "And who do you think will be the first adopters of this genious idea?");
				userHandler.setLunaState(8);
				break;	
				
			case 8:
				userHandler.getIdeaMap().put(LeanQuestion.EARLY_ADOPTERS , msg);
				session.sendMessage(channel, "I'm sure it will take no time for them to adopt it :wink:  ");
				session.sendMessage(channel, "Set yourselve mesurable realistic goals within a timeframe. What are they?");
				userHandler.setLunaState(9);
				break;
				
			case 9:
				userHandler.getIdeaMap().put(LeanQuestion.KEY_METRICS , msg);
				session.sendMessage(channel, "What methods are you going to use to promote your idea and reach new potential users?");
				userHandler.setLunaState(10);
				break;
			case 10:
				userHandler.getIdeaMap().put(LeanQuestion.CHANNELS , msg);
				session.sendMessage(channel, "This idea is coming up really well! We're almost done with the brainstorming!");
				session.sendMessage(channel, "Let's Talk :moneybag:  What will be the expenses of realizing and supporting the project?");
				userHandler.setLunaState(11);
				break;
			case 11:
				userHandler.getIdeaMap().put(LeanQuestion.COST , msg);
				session.sendMessage(channel, "And what are going the be the sources of revenue once its deployed?");
				userHandler.setLunaState(12);
				break;
			case 12:
				userHandler.getIdeaMap().put(LeanQuestion.REVENEUS , msg);
				session.sendMessage(channel, "This is once of the best ideas I ever heard!!! :heart_decoration:  :heart_decoration:  :heart_decoration: ");
				session.sendMessage(channel, "Give me a few seconds and I will generate a nice lean diagram for you!");
				session.sendMessage(channel, "//TODO ED MAKE THE DIAGRAM POP HERE");
				session.sendMessage(session.findChannelByName("innovation"), " Here's " + user.getRealName() + " amazing new idea!!!");
				session.sendMessage(session.findChannelByName("innovation"), userHandler.mapToString());
				
				userHandler.setLunaState(-2);
				break;

		}

	}

}
