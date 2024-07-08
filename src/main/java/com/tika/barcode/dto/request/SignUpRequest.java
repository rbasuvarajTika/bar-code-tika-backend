package com.tika.barcode.dto.request;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Processes an {@link SignUpRequest } request.
 * @author Raghu
 *
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	@NotBlank
	private String userName;
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	private String email;
	@NotBlank
	private String password;
	private String confirmPassword;
	private String otherPassword;
	private Date passwordUpdatedDate;
	private String phone;
	private String address;
	private String role;
	private String type;
	private String city;
	private String state;
	private String zip;
	private String image;
	private String salesForce;
	private String userStatusFlag;
	private String createdUser;
	private Date createdDate;
	private String updatedUser;
	private Date updateDate;
}
