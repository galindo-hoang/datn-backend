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
    public AccountEntity merge(AccountEntity oldData, AccountEntity newData) {
        oldData.setPhoneNumber(newData.getPhoneNumber() != null ? newData.getPhoneNumber() : oldData.getPhoneNumber());
        oldData.setPassword(newData.getPassword() != null ? newData.getPassword() : oldData.getPassword());
        oldData.setUserName(newData.getUserName() != null ? newData.getUserName() : oldData.getUserName());
        oldData.setImagePath(newData.getImagePath() != null ? newData.getImagePath() : oldData.getImagePath());
        oldData.setBirthday(newData.getBirthday() != null ? newData.getBirthday() : oldData.getBirthday());

        return oldData;
    }
}