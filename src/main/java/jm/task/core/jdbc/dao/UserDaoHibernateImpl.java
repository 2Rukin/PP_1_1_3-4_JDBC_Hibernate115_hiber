package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        final String sql = "CREATE TABLE if not exists `users` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `lastName` varchar(45) NOT NULL,\n" +
                "  `age` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();

        }


    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }

    }

//    @Override
//    public List<User> getAllUsers() {
//        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
//        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
//        Root<User> rootEntry = userCriteriaQuery.from(User.class);
//        CriteriaQuery<User> all = userCriteriaQuery.select(rootEntry);
//
//        TypedQuery<User> allQuery = sessionFactory.openSession().createQuery(all);
//        return allQuery.getResultList();
//
//    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            List<User> userList = session
                    .createQuery("SELECT a FROM User a", User.class).getResultList();

            return userList.size() > 0 ? userList : new ArrayList<>();
        }

//    @Override
//    public List<User> getAllUsers() {
////        List<User> userList = new ArrayList<>()
//
//        Session session = sessionFactory.openSession();
//        String hql = "SELECT FROM ";
//        session.getTransaction();
//        Query<User> query = session.createQuery(hql, User.class);
////        session.getTransaction().commit();
//
//        return query.list();
//    }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            final String sql = "TRUNCATE TABLE users;";
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
