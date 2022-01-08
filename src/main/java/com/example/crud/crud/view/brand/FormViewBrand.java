package com.example.crud.crud.view.brand;

import com.example.crud.crud.entity.Brand;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import elemental.json.Json;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static com.example.crud.crud.utils.fileUtil.generateFileNameWithTimestamp;

public class FormViewBrand extends FormLayout {
    MemoryBuffer memoryBuffer = new MemoryBuffer();
    Binder<Brand> binder = new BeanValidationBinder<>(Brand.class);

    TextField name = new TextField("Name");
    TextField address = new TextField("Address");
    DatePicker yearOfCreation = new DatePicker("Date of creation");
    Upload logo = new Upload(memoryBuffer);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Brand brand;

    public FormViewBrand(List<Brand> brands) {
        addClassName("form-view");
        binder.bindInstanceFields(this);
        add(
                createFormInputLayout()
        );
    }

    private Component createFormInputLayout() {
        name.setWidthFull();
        address.setWidthFull();

        logo.setWidthFull();
        logo.setMaxFiles(1);

        logo.addFinishedListener(e -> {

            String fileName = generateFileNameWithTimestamp(memoryBuffer.getFileName());
            InputStream inputStream = null;
            File file = new File("src/main/webapp/" + fileName);
            try {
                FileUtils.copyInputStreamToFile(memoryBuffer.getInputStream(), file);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            brand.setLogo(file);
        });

        return new VerticalLayout(name, address, createDatePicker(), logo, createButtonLayout());
    }

    private Component createDatePicker() {
        yearOfCreation.setWidthFull();
        yearOfCreation.setMax(LocalDate.now());
        return yearOfCreation;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
        binder.readBean(brand);
    }

    private Component createButtonLayout() {
        addClassName("Buttons");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new FormViewBrand.DeleteEvent(this, brand)));
        cancel.addClickListener(event -> fireEvent(new FormViewBrand.CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(brand);
            fireEvent(new FormViewBrand.SaveEvent(this, brand));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class ContactFormEvent extends ComponentEvent<FormViewBrand> {
        private Brand brand;

        protected ContactFormEvent(FormViewBrand source, Brand brand) {
            super(source, false);
            this.brand = brand;
        }

        public Brand getBrand() {
            return brand;
        }
    }

    public static class SaveEvent extends FormViewBrand.ContactFormEvent {
        SaveEvent(FormViewBrand source, Brand brand) {
            super(source, brand);
        }
    }

    public static class DeleteEvent extends FormViewBrand.ContactFormEvent {
        DeleteEvent(FormViewBrand source, Brand brand) {
            super(source, brand);
        }

    }

    public static class CloseEvent extends FormViewBrand.ContactFormEvent {
        CloseEvent(FormViewBrand source) {
            super(source, null);
        }
    }

    protected <T extends ComponentEvent<?>> Registration addBrandListener(Class<T> eventType,
                                                                          ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
