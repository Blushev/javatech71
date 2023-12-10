import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class UserService {
    public boolean registerUser(String username, String password, String email) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            UserRegistration userRegistration = new UserRegistration(username, password, email);
            session.save(userRegistration); // Сохраните данные регистрации пользователя

            UserAuthorization userAuthorization = new UserAuthorization();
            userAuthorization.setUsername(username);
            userAuthorization.setPassword(password);
            session.save(userAuthorization);

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


            List<UserAuthorization> users = query.getResultList();
            System.out.println("Query: " + query.unwrap(org.hibernate.query.Query.class).getQueryString());
            System.out.println("Users found: " + users);

            return users.isEmpty() ? null : users.get(0);
        } catch (NoResultException e) {
            return null;
        }
    }
}