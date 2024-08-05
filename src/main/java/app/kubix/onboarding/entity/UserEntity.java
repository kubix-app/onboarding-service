package app.kubix.onboarding.entity;

import app.kubix.onboarding.enums.RoleStatus;
import app.kubix.onboarding.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

	@Id
	private UUID userId;

	@Column(name = "email", unique = true)
	private String email;

	private String fullname;

	private String password;

	private String companyName;

	private String typeOfUse;
	private String whereDidYouHear;

	private String employeeNumber;
	private String employeeTitle;
	private String address;

	private String country;

	private String phoneNumber;

	private Instant activatedAt;

	private String manager;

	private String department;

	@Enumerated(EnumType.STRING)
	private RoleStatus roleStatus;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@JsonIgnore(value = true)
	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	public UserEntity(UserStatus userStatus, UUID userId, String email, String fullname, String password,
			String companyName, String typeOfUse, String whereDidYouHear, RoleStatus roleStatus) {
		this.userStatus = userStatus;
		this.userId = userId;
		this.email = email;
		this.fullname = fullname;
		this.password = password;
		this.companyName = companyName;
		this.typeOfUse = typeOfUse;
		this.whereDidYouHear = whereDidYouHear;
		this.roleStatus = roleStatus;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
