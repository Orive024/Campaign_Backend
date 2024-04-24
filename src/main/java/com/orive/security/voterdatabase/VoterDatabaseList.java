package com.orive.security.voterdatabase;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "voterdatabaselist")
public class VoterDatabaseList {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long sl;
	    
	    @Column(name = "votername")
	    private String votername;
	    
	    @Column(name = "mobilenumber")
	    private String mobilenumber;
	    
	    @Column(name = "state")
	    private String state;
	    
	    @Column(name = "district")
	    private String district;
	    
	    @Column(name = "pincode")
	    private String pincode;
}
