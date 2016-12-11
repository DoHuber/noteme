@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    static class NotLoggedInException extends Exception {
    }

    String getUserEmail() throws NotLoggedInException;

    String greetServer(String name) throws NotLoggedInException;
}

public interface GreetingServiceAsync

{
    void getUserEmail(AsyncCallback<String> callback);

    void greetServer(String input, AsyncCallback<String> callback);
}

    On the
    server side, calls
    must be
    made to
    UserService methods
    provide by Google.

@SuppressWarnings(“serial”)
public class GreetingServiceImpl extends RemoteServiceServlet
        implements GreetingService {

    /**
     * Check whether the user is logged in. Return the current User information or
     * throw an exception.
     *
     * @return String
     * @throws GreetingService.NotLoggedInException
     */

    public User checkUserLoggedIn() throws GreetingService.NotLoggedInException {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) throw new GreetingService.NotLoggedInException();
        return userService.getCurrentUser();
    }

    /**
     * Returns email address of the user logged in.
     *
     * @return String
     * @throws GreetingService.NotLoggedInException
     */

    public String getUserEmail() throws GreetingService.NotLoggedInException {
// Before we do anything, check to see if the user is logged in.
        User user = checkUserLoggedIn();
        return user.getEmail();
    }

    /**
     * Returns information about the user and what is running on the server.
     *
     * @return String
     * @throws GreetingService.NotLoggedInException
     */

    public String greetServer(String name) throws GreetingService.NotLoggedInException {
        User user = checkUserLoggedIn();                                                             // added this to ensure user is logged in

// Construct a greeting message.
        String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader(“User - Agent”);
        return “Hello, ”+name + “! < br ><br > I am running ”+serverInfo
                + “.<br ><br > It looks like you are using:<br >”+userAgent
                + “.<br ><br > You logged in with email: ”
        +user.getEmail();                        // added extra lines to show email address
    }

} // end class