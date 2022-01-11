package com.example.crud.crud.view.car;

import com.example.crud.crud.entity.Brand;
import com.example.crud.crud.entity.Car;
import com.example.crud.crud.service.BrandService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;


public class FormViewCar extends FormLayout {
    BrandService brandService;
    MemoryBuffer memoryBuffer = new MemoryBuffer();
    Binder<Car> binder = new BeanValidationBinder<>(Car.class);

    ComboBox<Brand> brand = new ComboBox<>("Brand");
    TextField model = new TextField("Model");
    DatePicker dateOfProduction = new DatePicker("Date of production");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Car car;

    public FormViewCar(List<Car> cars,BrandService brandService) {
        this.brandService = brandService;
        addClassName("form-view");
        binder.bindInstanceFields(this);
        add(
                createFormInputLayout()
        );
    }

    private Component createFormInputLayout() {
        brand.setItems(brandService.getBrands());
        brand.setItemLabelGenerator(Brand::getName);
        brand.setWidthFull();

        model.setWidthFull();

        return new VerticalLayout(brand, model, createDatePicker(), createButtonLayout());
    }

    private Component createDatePicker() {
        dateOfProduction.setWidthFull();
        dateOfProduction.setMax(LocalDate.now());
        return dateOfProduction;
    }

    public void setCar(Car car) {
        this.car = car;
        binder.readBean(car);
    }

    private Component createButtonLayout() {
        addClassName("Buttons");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, car)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, delete, cancel);
        return horizontalLayout;
    }

    private void validateAndSave() {
        try {
            binder.writeBean(car);
            fireEvent(new SaveEvent(this, car));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<FormViewCar> {
        private Car car;

        protected ContactFormEvent(FormViewCar source, Car car) {
            super(source, false);
            this.car = car;
        }

        public Car getCar() {
            return car;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(FormViewCar source, Car car) {
            super(source, car);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(FormViewCar source, Car car) {
            super(source, car);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(FormViewCar source) {
            super(source, null);
        }
    }

    protected <T extends ComponentEvent<?>> Registration addCarListener(Class<T> eventType,
                                                                        ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
