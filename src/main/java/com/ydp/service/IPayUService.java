/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.ydp.service;

import java.util.Map;

/**
 *  
 *  @version     1.0, 08-Mar-2015
 *  @author priyanka
 */
public interface IPayUService {
    
    public boolean makePayment(Map<String, String> params);

}
