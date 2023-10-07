import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UserService {
    public boolean registerUser(String username, String password, String email) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            UserRegistration user = new UserRegistration(username, password, email);
            session.save(user); // Сохраните данные регистрации пользователя

            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserAuthorization authenticateUser(String username, String password) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM UserAuthorization WHERE username = :username AND password = :password")
                    .setParameter("username", username)
                    .setParameter("password", password);

            UserAuthorization user = (UserAuthorization) query.getSingleResult();
            return user;
        }
    }
}