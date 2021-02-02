package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert MovieSession entity "
                    + movieSession, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> getMovieSession = session
                    .createQuery("SELECT m FROM MovieSession m "
                            + "WHERE m.movie.id = :movieId "
                                    + "AND date_format(m.showTime, '%Y-%m-%d') "
                                    + "= :date",
                            MovieSession.class);
            getMovieSession.setParameter("movieId", movieId);
            getMovieSession.setParameter("date", DateTimeFormatter.ISO_LOCAL_DATE.format(date));
            return getMovieSession.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find available sessions where movie Id: "
                    + movieId + " and Local date time: " + date, e);
        }
    }
}