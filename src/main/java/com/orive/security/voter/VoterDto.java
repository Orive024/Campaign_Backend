package com.orive.security.voter;


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
public class VoterDto {

	private Long sl;
    private String votername;
    private String mobilenumber;
    private String state;
    private String district;    
    private String pincode;
}
