package com.example.crud.crud.view;

import com.example.crud.crud.view.brand.ListViewBrand;
import com.example.crud.crud.view.car.ListViewCar;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
        setDrawerOpened(false);
    }

    private void createDrawer() {
        H1 logo = new H1("K.Konczyk");
        logo.addClassNames("text-l", "m-m");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
        addToNavbar(header);
    }

    private void createHeader() {
        RouterLink carsLink = new RouterLink("Cars", ListViewCar.class);
        RouterLink brandsLink = new RouterLink("Brands", ListViewBrand.class);
        carsLink.setHighlightCondition(HighlightConditions.sameLocation());
        brandsLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(carsLink, brandsLink));
    }
}
