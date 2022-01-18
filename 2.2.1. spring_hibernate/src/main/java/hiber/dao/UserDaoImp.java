package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.TypedQuery;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        sessionFactory.getCurrentSession().persist(user);
        session.getTransaction().commit();
    }

    @Override
    public void add(User user, Car car) {
        Session session = sessionFactory.openSession();
        user.setCarId(car);
        session.beginTransaction();
        sessionFactory.getCurrentSession().persist(user);
        session.getTransaction().commit();
    }

    @Override
    public User findUserByCar(String model, int series) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<User> query = session.createQuery(
                            "SELECT u FROM User u WHERE u.id = " +
                                    "(SELECT c.id FROM Car c WHERE c.model = :model AND c.series = :series)",
                            User.class)
                    .setParameter("model", model)
                    .setParameter("series", series);
            User user = query.getSingleResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

}
