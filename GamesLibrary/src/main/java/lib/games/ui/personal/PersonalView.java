package lib.games.ui.personal;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.authentication.CurrentUser;
import lib.games.data.User;
import lib.games.ui.Layout;



@Route(value = "personal", layout = Layout.class)
public class PersonalView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    public static final String VIEW_NAME = "Personal";

    public PersonalView() {

        H1 title = new H1(AccessControlFactory.getInstance().createAccessControl().getPrincipalName());
        add(title);

        TextField realname = new TextField("Real name");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");

        User user = AccessControlFactory.getInstance().createAccessControl().getUser();
        if (user != null) {
            realname.setValue(user.getFullName());
            email.setValue(user.getEmail());
            password.setValue(user.getPassword());
        }
        add(realname, email, password);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!AccessControlFactory.getInstance().createAccessControl().isUserSignedIn()) {
            UI.getCurrent().navigate("login");
            UI.getCurrent().getPage().reload();
        }
    }

    public void setParameter(BeforeEvent event, String parameter) {}
}
