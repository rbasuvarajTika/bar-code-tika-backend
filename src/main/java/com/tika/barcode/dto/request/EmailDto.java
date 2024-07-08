package com.tika.barcode.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *  @author Raghu
 * The {@code EmailDto} class
 * <br> {@code @Data} annotation is used to generate
 * <br>
 * <i>Getters, Setters, Parameterized Constructor, toString, equals and HashCode methods</i>
 */
@AllArgsConstructor
@Getter
@Setter
public class EmailDto {
		private String mailFrom;
	 
		private String mailTo;
	 
		private String mailCc;
	 
		private String mailBcc;
	 
		private String mailSubject;
	 
		private String mailContent;
	 
		private String templateName;
	 
		private String contentType;

		public EmailDto() {
			this.contentType = "text/html";
		}
		
		@Override
		public String toString() {
			return "Email [mailFrom=" + mailFrom + ", mailTo=" + mailTo + ", mailCc=" + mailCc + ", mailBcc=" + mailBcc
					+ ", mailSubject=" + mailSubject + ", mailContent=" + mailContent + ", templateName=" + templateName
					+ ", contentType=" + contentType + "]";
		}
}
