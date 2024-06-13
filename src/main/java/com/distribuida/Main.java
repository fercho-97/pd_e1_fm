package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.services.BookService;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

import java.util.List;
import com.google.gson.Gson;


public class Main {

    static Gson gson = new Gson();
    private static ContainerLifecycle lifecycle = null;

    public static void main(String[] args)  {
        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);
        BookService servicio = CDI.current().select(BookService.class).get();

        WebServer server = WebServer.builder()
                .port(9091)
                .routing(builder -> builder
                        .get("/books", (req, res) -> {
                            List<Book> books = servicio.findAll();
                            String response = gson.toJson(books);
                            res.status(200).send(response);
                        }).post("/books", (req, res) -> {
                            try {
                                String body = req.content().as(String.class);
                                Book book1 = gson.fromJson(body, Book.class);
                                servicio.insert(book1);
                                res.status(201).send("Se ha insertado el libro");
                            } catch (Exception e) {
                                res.status(400).send("Error al procesar la solicitud: " + e.getMessage());
                            }
                        }).post("/books", (req, res) -> {
                            try {
                                String body = req.content().as(String.class);
                                Book book1 = gson.fromJson(body, Book.class);
                                servicio.insert(book1);
                                res.status(201).send("Se ha insertado el libro");
                            } catch (Exception e) {
                                res.status(400).send("Error al procesar la solicitud: " + e.getMessage());
                            }
                        }).put("/books/{id}", (req, res) -> {
                            try {
                                String body = req.content().as(String.class);
                                Book book1 = gson.fromJson(body, Book.class);
                                Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                                book1.setId(id);
                                if (servicio.update(book1)) {
                                    res.status(200).send("Libro actualizado con id " + id);
                                } else {
                                    res.status(404).send("El libro con id " + id + " no se encontro.");
                                }
                            } catch (Exception e) {
                                res.status(400).send("Error al procesar la solicitud: " + e.getMessage());
                            }
                        }).delete("/books/{id}", (req, res) -> {
                            try {
                                Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                                if (servicio.delete(id)) {
                                    res.status(200).send("Libro eliminado con id " + id);
                                } else {
                                    res.status(404).send("El libro con id " + id + " no se encontro.");
                                }
                            } catch (Exception e) {
                                res.status(400).send("Error al procesar la solicitud: " + e.getMessage());
                            }
                        }).get("/books/{id}", (req, res) -> {
                            try {
                                Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                                Book book = servicio.findById(id);
                                if (book != null) {
                                    String response = gson.toJson(book);
                                    res.status(200).send(response);
                                } else {
                                    res.status(404).send("El libro con id " + id + " no se encontro.");
                                }
                            } catch (Exception e) {
                                res.status(400).send("Error al procesar la solicitud: " + e.getMessage());
                            }
                        })

                )
                .build();

        server.start();

    }

}