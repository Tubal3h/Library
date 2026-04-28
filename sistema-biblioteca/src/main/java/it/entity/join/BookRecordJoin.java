package it.entity.join;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */

import it.entity.User;

/**
 * Entità che rappresenta la vista aggregata di un libro nel sistema,
 * ottenuta tramite JOIN tra le tabelle books, edition, books_names, author, publisher e category.
 */
public class BookRecordJoin {

    private RentalRecordJoin rentalRecordJoin;
    private User user;


    /**
     * Costruttore di default.
     */
    public BookRecordJoin() {
    }

    /**
     * Costruttore con parametri completi.
     */
    public BookRecordJoin(
        RentalRecordJoin rentalRecordJoin,
        User user
    ) {
        this.rentalRecordJoin = rentalRecordJoin;
        this.user = user;

    }


   public RentalRecordJoin getRentalRecordJoin() {
       return rentalRecordJoin;
   }

   public void setRentalRecordJoin(RentalRecordJoin rentalRecordJoin) {
       this.rentalRecordJoin = rentalRecordJoin;
   }

   public User getUser() {
       return user;
   }

   public void setUser(User user) {
       this.user = user;
   }

    @Override
    public String toString() {
        return "BookRecordJoin [rentalRecordJoin=" + rentalRecordJoin + ", user=" + user + "]";
    }
}
