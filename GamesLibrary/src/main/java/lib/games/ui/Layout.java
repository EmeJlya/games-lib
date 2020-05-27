package lib.games.ui;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import lib.games.authentication.AccessControl;
import lib.games.authentication.AccessControlFactory;
import lib.games.data.AccessLevel;
import lib.games.ui.games.GamesView;

public class Layout extends AppLayout implements RouterLayout {

    private final Button logoutButton;

    public Layout() {



        final DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("menu-toggle");
        addToNavbar(drawerToggle);


        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        top.setClassName("menu-header");


        final Label title = new Label("Games Library");
        top.add(title);
        addToNavbar(top);


        logoutButton = createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e -> logout());
        logoutButton.getElement().setAttribute("title", "Logout (Ctrl+L)");

    }

    private void logout() {
        AccessControlFactory.getInstance().createAccessControl().signOut();
    }

    private RouterLink createMenuLink(Class<? extends Component> viewClass,
                                      String caption, Icon icon) {
        final RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        icon.setSize("24px");
        return routerLink;
    }

    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        routerButton.setIcon(icon);
        icon.setSize("24px");
        return routerButton;
    }

    private void registerAdminViewIfApplicable(AccessControl accessControl) {

        if (accessControl.isUserInRole(AccessLevel.ADMINISTRATOR)
                && !RouteConfiguration.forSessionScope()
                .isRouteRegistered(GamesView.class)) {
            RouteConfiguration.forSessionScope().setRoute(GamesView.VIEW_NAME,
                    GamesView.class, Layout.class);

        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        attachEvent.getUI().addShortcutListener(() -> logout(), Key.KEY_L,
                KeyModifier.CONTROL);

        final AccessControl accessControl = AccessControlFactory.getInstance()
                .createAccessControl();
        if (accessControl.isUserInRole(AccessLevel.MODERATOR)) {

            registerAdminViewIfApplicable(accessControl);

            addToDrawer(createMenuLink(GamesView.class, GamesView.VIEW_NAME,
                    VaadinIcon.DOCTOR.create()));
        }

        addToDrawer(logoutButton);
    }

}
