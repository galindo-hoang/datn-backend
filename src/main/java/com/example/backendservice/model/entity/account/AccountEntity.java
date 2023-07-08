package com.example.backendservice.model.entity.account;


import com.example.backendservice.common.model.BaseEntity;
import com.example.backendservice.common.model.UserRole;
import com.example.backendservice.common.utils.DataAdapter;
import com.example.backendservice.model.entity.prescription.PrescriptionEntity;
import com.example.backendservice.model.entity.reminder.ReminderEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity extends BaseEntity implements DataAdapter<AccountEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String userName;
    private String imagePath;
    private Timestamp birthday;
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
    private UserRole userRole = UserRole.ANONYMOUS;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PrescriptionEntity> prescriptions;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReminderEntity> reminders;

    @Override
    public AccountEntity merge(AccountEntity newData) {
        this.setPhoneNumber(newData.getPhoneNumber() != null ? newData.getPhoneNumber() : this.getPhoneNumber());
        this.setPassword(newData.getPassword() != null ? newData.getPassword() : this.getPassword());
        this.setUserName(newData.getUserName() != null ? newData.getUserName() : this.getUserName());
        this.setImagePath(newData.getImagePath() != null ? newData.getImagePath() : this.getImagePath());
        this.setBirthday(newData.getBirthday() != null ? newData.getBirthday() : this.getBirthday());

        return this;
    }
}