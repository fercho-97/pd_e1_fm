package com.distribuida.services;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.util.List;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    @Inject
    private EntityManager em;

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory( "pu-distribuida");
//
//    EntityManager em = emf. createEntityManager ();

    @Override
    public void insert(Book libro) {
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No se pudo persistir el libro");
            em.getTransaction().rollback();
        }
    }

    @Override
    public Book findById(int id) {
        return em.find(Book.class, id);
    }
    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();

    }

    @Override
    public Boolean update(Book libro) {
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println("No se pudo actualizar el libro");
            em.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            em.getTransaction().begin();
            Book libro = this.findById(id);
            if (libro != null) {
                em.remove(libro);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Libro no encontrado");
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el objeto");
            em.getTransaction().rollback();
            return false;
        }
    }
}
