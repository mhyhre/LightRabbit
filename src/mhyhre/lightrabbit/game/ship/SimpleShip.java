/*
 * Copyright (C) 2013-2014 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 * 
 * This work is licensed under a Creative Commons 
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * You may obtain a copy of the License at
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/legalcode
 *
 */

package mhyhre.lightrabbit.game.ship;


public abstract class SimpleShip {
    
    ShipConfiguration configuration;
    
    public SimpleShip(ShipConfiguration config) {
        configuration = config;
    }
    
}
