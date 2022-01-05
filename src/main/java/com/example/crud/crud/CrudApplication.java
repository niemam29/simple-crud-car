package com.example.crud.crud;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@PWA(name = "Car CRUD", shortName = "CCRUD", offlinePath="offline.html", offlineResources = { "./images/offline.png"})
@CssImport("style2s.css")
@PageTitle("Car CRUD")
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }


}
