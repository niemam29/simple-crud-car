package com.example.crud.crud.view.brand;

import com.example.crud.crud.entity.Brand;
import com.example.crud.crud.repository.BrandRepository;
import com.example.crud.crud.service.BrandService;
import com.example.crud.crud.view.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import elemental.json.Json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@PageTitle("Car CRUD")
@Route(value = "/brand", layout = MainLayout.class)
public class ListViewBrand extends VerticalLayout {
    H1 title = new H1("Brands");
    Grid<Brand> grid = new Grid<>(Brand.class);
    TextField textField = new TextField();
    FormViewBrand formViewBrand;
    Button refresh = new Button(new Icon(VaadinIcon.REFRESH));
    private BrandService brandService;

    public ListViewBrand(BrandService brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureFrom();

        add(getToolbar(), getContent());

        addItemsToGrid();
        closeForm();
    }


    private Component getContent() {
        HorizontalLayout layout = new HorizontalLayout(grid, formViewBrand);
        layout.setFlexGrow(2, grid);
        layout.setFlexGrow(1, formViewBrand);
        layout.addClassName("content");
        layout.setSizeFull();
        return layout;
    }

    private void addItemsToGrid() {
        grid.setItems(brandService.getBrands());
    }

    private void refreshItemsOnGrid() {
        //cleaning list of uploaded files
        formViewBrand.logo.getElement().setPropertyJson("files", Json.createArray());
        grid.setItems(brandService.getBrandByFilter(textField.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("brand-grid");
        grid.setSizeFull();
        grid.setColumns("id_brand", "name", "address", "yearOfCreation");

        grid.addComponentColumn(this::createLogo);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editBrand(e.getValue()));
    }

    public Component getToolbar() {
        textField.setPlaceholder("Filter by name");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e -> refreshItemsOnGrid());
        Button addCarButton = new Button("Add brand");

        addCarButton.addClickListener(e -> addBrand());
        refresh.addClickListener(e -> refreshItemsOnGrid());

        HorizontalLayout toolbar = new HorizontalLayout(textField, addCarButton, refresh);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void closeForm() {
        formViewBrand.setBrand(null);
        formViewBrand.setVisible(false);
        removeClassName("editing");
    }

    private void configureFrom() {
        formViewBrand = new FormViewBrand(brandService.getBrands());
        formViewBrand.setWidth("25em");

        formViewBrand.addBrandListener(FormViewBrand.SaveEvent.class, this::saveCar);
        formViewBrand.addBrandListener(FormViewBrand.DeleteEvent.class, this::deleteCar);
        formViewBrand.addBrandListener(FormViewBrand.CloseEvent.class, this::closeEvent);
    }

    private void saveCar(FormViewBrand.SaveEvent event) {
        brandService.addBrand(event.getBrand());
        refreshItemsOnGrid();
        closeForm();
    }

    private void closeEvent(FormViewBrand.CloseEvent event) {
        refreshItemsOnGrid();
        closeForm();
    }

    private void deleteCar(FormViewBrand.DeleteEvent event) {
        brandService.deleteBrand(event.getBrand());
        refreshItemsOnGrid();
        closeForm();
    }

    private void editBrand(Brand brand) {
        if (brand == null) {
            closeForm();
        } else {
            formViewBrand.setBrand(brand);
            formViewBrand.setVisible(true);
            addClassName("editing");
        }
    }

    private void addBrand() {
        grid.asSingleSelect().clear();
        editBrand(new Brand());
    }

    private Image createLogo(Brand brand) {
        Image image = new Image(brand.getLogo().getName(), "logo");
        image.setMaxHeight("30px");
        return image;
    }
}
