package com.example.crud.crud.view;

import com.example.crud.crud.car.Car;
import com.example.crud.crud.car.CarRepository;
import com.example.crud.crud.car.CarService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Main")
@Route(value = "",layout = MainLayout.class)
public class ListView extends VerticalLayout {
    H1 title = new H1("Cars");
    Grid<Car> grid = new Grid<>(Car.class);
    TextField textField = new TextField();
    CarFormView carFormView;
    Button refresh = new Button(new Icon(VaadinIcon.REFRESH));
    private CarService carService;

    public ListView(CarService carService, CarRepository carRepository) {
        this.carService = carService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureFrom();

        add(getToolbar(), getContent());

        addItemsToGrid();

        closeForm();
    }

    private void closeForm() {
        carFormView.setCar(null);
        carFormView.setVisible(false);
        removeClassName("editing");
    }

    private void configureFrom() {
        carFormView = new CarFormView(carService.getCars());
        carFormView.setWidth("25em");

        carFormView.addListener(CarFormView.SaveEvent.class, this::saveCar);
        carFormView.addListener(CarFormView.DeleteEvent.class,this::deleteCar);
        carFormView.addListener(CarFormView.CloseEvent.class,this::closeEvent);

    }

    private void saveCar(CarFormView.SaveEvent event) {
        carService.addCar(event.getCar());
        refreshItemsOnGrid();
        closeForm();
    }
    private void closeEvent(CarFormView.CloseEvent event) {
        refreshItemsOnGrid();
        closeForm();
    }
    private void deleteCar(CarFormView.DeleteEvent event) {
        carService.deleteCar(event.getCar());
        refreshItemsOnGrid();
        closeForm();
    }

    private Component getContent() {
        HorizontalLayout layout = new HorizontalLayout(grid, carFormView);
        layout.setFlexGrow(2, grid);
        layout.setFlexGrow(1, carFormView);
        layout.addClassName("content");
        layout.setSizeFull();
        return layout;
    }

    private void addItemsToGrid() {
        grid.setItems(carService.getCars());
    }

    private void refreshItemsOnGrid() {
        grid.setItems(carService.getCarsByBrand(textField.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("car-grid");
        grid.setSizeFull();
        grid.setColumns("id", "brand", "model", "age");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCar(e.getValue()));
    }

    private void editCar(Car car) {
        if (car == null) {
            closeForm();
        }else{
            carFormView.setCar(car);
            carFormView.setVisible(true);
            addClassName("editing");
        }
    }

    public Component getToolbar() {
        textField.setPlaceholder("Filter by name");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e -> refreshItemsOnGrid());
        Button addCarButton = new Button("Add car");

        addCarButton.addClickListener(e-> addCar());

        refresh.addClickListener(e->refreshItemsOnGrid());

        HorizontalLayout toolbar = new HorizontalLayout(textField, addCarButton,refresh);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCar() {
        grid.asSingleSelect().clear();
        editCar(new Car());
    }
}
