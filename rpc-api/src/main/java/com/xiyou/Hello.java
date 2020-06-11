package com.xiyou;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



/**
 * @author xiyou
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hello implements Serializable {
    private String message;
    private String description;


}
