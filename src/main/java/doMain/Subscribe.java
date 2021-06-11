package doMain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="subscribe")
public class Subscribe {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;
    @ManyToOne
    @JoinColumn(name="magazine_id", referencedColumnName="id")
    private Magazine magazine;
    @Column(name="subscribe_status")
    private boolean subscribeStatus;
    @Column(name="subscribe_date")
    private LocalDate subscribeDate;
    @Column(name="subscribe_period")
    private int subscribePeriod;

    public Subscribe() { }

    public Subscribe(int id, User user, Magazine magazine, boolean subscribeStatus, LocalDate subscribeDate,
                     int subscribePeriod) {
        this.id = id;
        this.user = user;
        this.magazine = magazine;
        this.subscribeStatus = subscribeStatus;
        this.subscribeDate = subscribeDate;
        this.subscribePeriod = subscribePeriod;
    }

    public Subscribe(User user, Magazine magazine, boolean subscribeStatus, LocalDate subscribeDate,
                     int subscribePeriod) {
        this.user = user;
        this.magazine = magazine;
        this.subscribeStatus = subscribeStatus;
        this.subscribeDate = subscribeDate;
        this.subscribePeriod = subscribePeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Magazine getMagazine() {
        return magazine;
    }

    public void setMagazine(Magazine magazine) {
        this.magazine = magazine;
    }

    public boolean getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(boolean subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public LocalDate getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(LocalDate subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    public int getSubscribePeriod() {
        return subscribePeriod;
    }

    public void setSubscribePeriod(int subscribePeriod) {
        this.subscribePeriod = subscribePeriod;
    }

    @Override
    public String toString() {
        if (id == 0)
            return "userID#" + user.getId() + ", magazineID#" + magazine.getId() + ", Подписка оформлена: " + subscribeStatus
                    + ", Дата оформления подписки: " + subscribeDate + ", Период подписки: " + subscribePeriod
                    + " мес.";
        else
            return "Subscribe ID#" + id + ": userID#" + user.getId() + ", magazineID#" + magazine.getId()
                    + ", Подписка оформлена: " + subscribeStatus + ", Дата оформления подписки: " + subscribeDate
                    + ", Период подписки: " + subscribePeriod + " мес.";
    }
}