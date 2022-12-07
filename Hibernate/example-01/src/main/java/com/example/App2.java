package com.example;


import com.example.entity.app2.Game;
import com.example.entity.app2.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Level;
import java.util.logging.Logger;


public class App2 {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        entityManagerFactory = Persistence.createEntityManagerFactory("h2DB");
        entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(new Game(1L, "Game 1"));
        entityManager.persist(new Game(2L, "Game 2"));
        entityManager.persist(new Player(1L, "Player 1"));
        entityManager.persist(new Player(2L, "Player 2"));
        entityManager.persist(new Player(3L, "Player 3"));
        entityManager.getTransaction().commit();

        entityManager.clear();
        entityManager.close();
    }

    public static void main(String[] args) {
        try {
            System.out.println("\n\n");

            whenUsingFindMethodToUpdateGame_thenExecutesSelectForGame(); // select and update
            whenUsingGetReferenceMethodToUpdateGame_thenExecutesSelectForGame(); // select and update
            whenUsingFindMethodToDeletePlayer_thenExecutesSelectForPlayer(); // select and delete
            whenUsingGetReferenceMethodToDeletePlayer_thenExecutesSelectForPlayer(); // select and delete
            whenUsingFindMethodToUpdatePlayersGame_thenExecutesSelectForGame(); // select select and update
            whenUsingGetReferenceMethodToUpdatePlayersGame_thenDoesNotExecuteSelectForGame(); // select and update

            System.out.println("\n\n");
            truncate();
        } finally {
            entityManagerFactory.close();
        }
    }

    private static void whenUsingFindMethodToUpdateGame_thenExecutesSelectForGame() {
        runInTransaction(() -> {
            Game game1 = entityManager.find(Game.class, 1L);
            game1.setName("Game Updated 1");
            entityManager.persist(game1);
        });
    }

    private static void whenUsingGetReferenceMethodToUpdateGame_thenExecutesSelectForGame() {
        runInTransaction(() -> {
            Game game1 = entityManager.getReference(Game.class, 1L);
            game1.setName("Game Updated 2");

            entityManager.persist(game1);
        });
    }

    private static void whenUsingFindMethodToDeletePlayer_thenExecutesSelectForPlayer() {
        runInTransaction(() -> {
            Player player2 = entityManager.find(Player.class, 2L);
            entityManager.remove(player2);
        });
    }

    private static void whenUsingGetReferenceMethodToDeletePlayer_thenExecutesSelectForPlayer() {
        runInTransaction(() -> {
            Player player3 = entityManager.getReference(Player.class, 3L);
            entityManager.remove(player3);
        });
    }

    private static void truncate() {
        runInTransaction(() -> {
            entityManager.createQuery("delete from " + Player.class.getName())
                    .executeUpdate();
            entityManager.createQuery("delete from " + Game.class.getName())
                    .executeUpdate();
        });
    }

    private static void whenUsingFindMethodToUpdatePlayersGame_thenExecutesSelectForGame() {
        runInTransaction((() -> {
            Game game1 = entityManager.find(Game.class, 1L);

            Player player1 = entityManager.find(Player.class, 1L);
            player1.setGame(game1);

            entityManager.persist(player1);
        }));
    }

    private static void whenUsingGetReferenceMethodToUpdatePlayersGame_thenDoesNotExecuteSelectForGame() {
        runInTransaction(() -> {
            Game game2 = entityManager.getReference(Game.class, 2L);

            Player player1 = entityManager.find(Player.class, 1L);
            player1.setGame(game2);

            entityManager.persist(player1);
        });
    }

    private static void runInTransaction(Runnable task) {
        /*
         * We create new persistence context for each test method to discard Hibernate
         * first level cache. So that we can see the behavior of getReference() method
         * in a non-cached environment.
         */
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        task.run();
        entityManager.getTransaction().commit();
        /*
         * In any case, we use clear() and close() to make all the managed entities
         * detached and cleared. So, we can be sure we test always on a clear
         * persistence context.
         */
        entityManager.clear();
        entityManager.close();
    }

}
