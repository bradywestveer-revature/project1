package frontcontroller;

import io.javalin.Javalin;
import org.apache.log4j.Logger;

public class FrontController {
	private static final Logger logger = Logger.getLogger (FrontController.class);
	
	public FrontController (Javalin webServer) {
		webServer.before (context -> {
			logger.info ("Request: " + context.method () + " " + context.path () + " " + context.body ());
		});
		
		new Dispatcher (webServer);
	}
}
