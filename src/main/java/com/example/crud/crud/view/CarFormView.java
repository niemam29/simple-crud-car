package com.example.crud.crud.view;

import com.example.crud.crud.car.Car;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;




public class CarFormView extends FormLayout {
    Binder<Car> binder = new BeanValidationBinder<>(Car.class);

    TextField brand = new TextField("Brand");
    TextField model = new TextField("Model");
    DatePicker dateOfProduction = new DatePicker("Date of production");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Car car;

    public CarFormView(List<Car> cars) {
        addClassName("form-view");
        binder.bindInstanceFields(this);
        add(
                brand,
                model,
                dateOfProduction,
                createButtonLayout()
        );
    }

    public void setCar(Car car) {
        this.car = car;
        binder.readBean(car);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, car)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(car);
            fireEvent(new SaveEvent(this, car));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<CarFormView> {
        private Car car;

        protected ContactFormEvent(CarFormView source, Car car) {
            super(source, false);
            this.car = car;
        }

        public Car getCar() {
            return car;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(CarFormView source, Car car) {
            super(source, car);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(CarFormView source, Car car) {
            super(source, car);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(CarFormView source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
