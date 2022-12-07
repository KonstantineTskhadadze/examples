package com.example;

import com.example.entity.app3.Account;
import com.example.entity.app3.AccountId;
import com.example.entity.app3.Book;
import com.example.entity.app3.BookId;
import jakarta.persistence.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App3 {
    private static final String SAVINGS_ACCOUNT = "Savings";
    private static final String ACCOUNT_NUMBER = "JXSDF324234";

    private static final String ENGLISH = "English";
    private static final String WAR_AND_PEACE = "War and Peace";

    private static EntityManagerFactory emf;
    private static EntityManager em;

    static {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        emf = Persistence.createEntityManagerFactory("h2DB");
        em = emf.createEntityManager();
    } public static void main(String[] args) {
        try {
            persistAccountWithCompositeKeyThenRetrieveDetails();
            persistBookWithCompositeKeyThenRetrieveDetails();
        } finally {
            truncate();
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static void persistAccountWithCompositeKeyThenRetrieveDetails() {
        Account savingsAccount = createAccount();
        persist(savingsAccount);
        clearThePersistenceContext();
        Account account = findAccountByAccountId();
        System.out.println(account);
        account = findAccountByAccountIdTypedQuery();
        System.out.println(account);
        account = findAccountByAccountIdNamedQuery();
        System.out.println(account);
        account = findAccountByAccountIdNativeQuery();
        System.out.println(account);
    }

    private static void persistBookWithCompositeKeyThenRetrieveDetails() {
        Book warAndPeace = createBook();
        persist(warAndPeace);
        clearThePersistenceContext();
        Book book = findBookByBookId();
        System.out.println(book);
        book = findBookByBookIdTypedQuery();
        System.out.println(book);
        book = findBookByBookIdNamedQuery();
        System.out.println(book);
        book = findBookByBookIdNativeQuery();
        System.out.println(book);
    }

    private static Account createAccount() {
        Account savingsAccount = new Account();
        savingsAccount.setAccountNumber(ACCOUNT_NUMBER);
        savingsAccount.setAccountType(SAVINGS_ACCOUNT);
        savingsAccount.setDescription("Savings account");
        return savingsAccount;
    }

    private static Book createBook() {
        BookId bookId = new BookId(WAR_AND_PEACE, ENGLISH);
        Book warAndPeace = new Book(bookId);
        warAndPeace.setDescription("Novel and Historical Fiction");
        return warAndPeace;
    }

    private static Account findAccountByAccountId() {
        return em.find(Account.class, new AccountId(ACCOUNT_NUMBER, SAVINGS_ACCOUNT));
    }

    private static Account findAccountByAccountIdTypedQuery() {
        TypedQuery<Account> query = em
                .createQuery("from Account a where a.accountNumber = ?1 and a.accountType = :accType", Account.class);
        query.setParameter(1, ACCOUNT_NUMBER);
        query.setParameter("accType", SAVINGS_ACCOUNT);
        return query.getSingleResult();
    }

    private static Account findAccountByAccountIdNamedQuery() {
        TypedQuery<Account> query = em.createNamedQuery("Account.findAccountByAccountId", Account.class);
        query.setParameter("accNum", ACCOUNT_NUMBER);
        query.setParameter("accType", SAVINGS_ACCOUNT);
        return query.getSingleResult();
    }

    private static Account findAccountByAccountIdNativeQuery() {
        Query query = em.createNativeQuery("SELECT * FROM Account WHERE accountNumber=?1 AND accountType=?2",
                Account.class);
        query.setParameter(1, ACCOUNT_NUMBER);
        query.setParameter(2, SAVINGS_ACCOUNT);
        return (Account) query.getSingleResult();
    }

    private static Book findBookByBookId() {
        return em.find(Book.class, new BookId(WAR_AND_PEACE, ENGLISH));
    }

    private static Book findBookByBookIdTypedQuery() {
        TypedQuery<Book> query = em
                .createQuery("from Book b where b.bookId.title = ?1 and b.bookId.language = :language", Book.class);
        query.setParameter(1, WAR_AND_PEACE);
        query.setParameter("language", ENGLISH);
        return query.getSingleResult();
    }

    private static Book findBookByBookIdNamedQuery() {
        TypedQuery<Book> query = em.createNamedQuery("Book.findBookByBookId", Book.class);
        query.setParameter(1, WAR_AND_PEACE);
        query.setParameter(2, ENGLISH);
        return query.getSingleResult();
    }

    private static Book findBookByBookIdNativeQuery() {
        Query query = em.createNativeQuery("SELECT * FROM Book b WHERE b.title = :title and b.language = :language", Book.class);
        query.setParameter("title", WAR_AND_PEACE);
        query.setParameter("language", ENGLISH);
        return (Book) query.getSingleResult();
    }

    private static void persist(Account account) {
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    private static void persist(Book book) {
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
    }

    private static void clearThePersistenceContext() {
        em.clear();
    }

    private static void truncate() {
        em.getTransaction().begin();
        em.createQuery("delete from " + Account.class.getName()).executeUpdate();
        em.createQuery("delete from " + Book.class.getName()).executeUpdate();
        em.getTransaction().commit();
    }
}

