package com.example.crud.crud.view.car;

import com.example.crud.crud.entity.Car;
import com.example.crud.crud.repository.CarRepository;
import com.example.crud.crud.service.CarService;
import com.example.crud.crud.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Car CRUD")
@Route(value = "", layout = MainLayout.class)
public class ListViewCar extends VerticalLayout {
    Grid<Car> grid = new Grid<>(Car.class);
    TextField textField = new TextField();
    FormViewCar formViewCar;
    Button refresh = new Button(new Icon(VaadinIcon.REFRESH));
    private CarService carService;

    public ListViewCar(CarService carService, CarRepository carRepository) {
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
        formViewCar.setCar(null);
        formViewCar.setVisible(false);
        removeClassName("editing");
    }

    private void configureFrom() {
        formViewCar = new FormViewCar(carService.getCars());
        formViewCar.setWidth("25em");

        formViewCar.addCarListener(FormViewCar.SaveEvent.class, this::saveCar);
        formViewCar.addCarListener(FormViewCar.DeleteEvent.class, this::deleteCar);
        formViewCar.addCarListener(FormViewCar.CloseEvent.class, this::closeEvent);

    }

    private void saveCar(FormViewCar.SaveEvent event) {
        carService.addCar(event.getCar());
        refreshItemsOnGrid();
        closeForm();
    }

    private void closeEvent(FormViewCar.CloseEvent event) {
        refreshItemsOnGrid();
        closeForm();
    }

    private void deleteCar(FormViewCar.DeleteEvent event) {
        carService.deleteCar(event.getCar());
        refreshItemsOnGrid();
        closeForm();
    }

    private Component getContent() {
        HorizontalLayout layout = new HorizontalLayout(grid, formViewCar);
        layout.setFlexGrow(2, grid);
        layout.setFlexGrow(1, formViewCar);
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
        } else {
            formViewCar.setCar(car);
            formViewCar.setVisible(true);
            addClassName("editing");
        }
    }

    public Component getToolbar() {
        textField.setPlaceholder("Filter by name");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e -> refreshItemsOnGrid());
        Button addCarButton = new Button("Add car");

        addCarButton.addClickListener(e -> addCar());

        refresh.addClickListener(e -> refreshItemsOnGrid());

        HorizontalLayout toolbar = new HorizontalLayout(textField, addCarButton, refresh);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCar() {
        grid.asSingleSelect().clear();
        editCar(new Car());
    }
}
