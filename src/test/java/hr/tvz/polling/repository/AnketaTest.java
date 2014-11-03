package hr.tvz.polling.repository;
import hr.tvz.polling.model.Anketa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
public class AnketaTest {
 
    private static EntityManager em;
 
    public static void main(String[] args) {
 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");
        em = emf.createEntityManager();
 
        createEmployee(1l, "Saint",  true);
        createEmployee(2l, "Jack",  false);
        createEmployee(3l, "Sam",  true);
 
    }
 
    private static void createEmployee(Long id, String option, Boolean active) {
        em.getTransaction().begin();
        Anketa emp = new Anketa(id, option, true);
        em.persist(emp);
        em.getTransaction().commit();
    }
}