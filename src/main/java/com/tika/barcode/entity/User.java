package com.tika.barcode.entity;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Processes an {@link User } .request
 * 
 * @author Raghu
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DIM_USER", schema = "dbo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private int userId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "Role")
	private String role;

	@Column(name = "USER_EMAIL")
	private String userMail;

	@Column(name = "USER_PHONE")
	private String phone;

	@Column(name = "USER_MOBILE")
	private String userMobile;

	@Column(name = "USER_EMP_ID")
	private String UserEmpID;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP")
	private String zip;

	@Column(name = "PREFERRED_NAME")
	private String preferredName;

	@Column(name = "ACTIVE_IND")
	private String activeInd;

	@Column(name = "USER_TERR")
	private String userTerr;

	@Column(name = "EMP_ID")
	private String empId;

	@Column(name = "HIRE_DATE")
	private Date hireDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "ADM_TOOLS_FLAG")
	private String admToolsFlag;

	@Column(name = "ATTENDEE_FLAG")
	private String attendeeFlag;

	@Column(name = "BOOKING_URL")
	private String bookingUrl;

	@Column(name = "MANAGER_EMAIL")
	private String managerEmail;

	@Column(name = "USER_TIME_ZONE")
	private String userTimeZone;

	@Column(name = "USER_NT_ID")
	private String userNtId;

	@Column(name = "OUTLOOK_CLIENT_ID")
	private String outlookClientId;

	@Column(name = "OUTLOOK_SECRET_CODE")
	private String outlookSecretCode;

	@Column(name = "OUTLOOK_EMAIL_ID")
	private String outlookEmailId;

	@Column(name = "SALES_FORCE")
	private String salesForce;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "CONFIRM_PASSWORD")
	private String confirmPassword;

	@Column(name = "PASSWORD_UPDATED_DATE")
	private Date passwordUpdatedDate;

	@Column(name = "USER_STATUS_FLAG")
	private String userStatusFlag;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "OTHER_PASSWORD")
	private String otherPassword;

	@Column(name = "USER_IMAGE_URL")
	private String userImageUrl;

	@Column(name = "CREATED_USER")
	private String createdUser;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_USER")
	private String updatedUser;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getUsername() {
		// email in our case
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
