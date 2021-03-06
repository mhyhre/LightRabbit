/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.game.weapons.guns;

import mhyhre.lightrabbit.MainActivity;
import mhyhre.lightrabbit.game.units.UnitMoveDirection;
import mhyhre.lightrabbit.game.units.models.UnitModel;
import mhyhre.lightrabbit.game.weapons.GunType;
import mhyhre.lightrabbit.game.weapons.projectiles.Bullet20Unit;
import mhyhre.lightrabbit.game.weapons.projectiles.Projectile;

public class AutoGun extends Gun {
    
    private static final float RELOADING_TIME = 0.2f;
    private static final float RELOADING_CLIP_TIME = 2.0f;
    private static final int CLIP_SIZE = 4;
    private static final int SHOT_POWER = 10;
    protected static float lashShootTime = 0;
    private float lastFireTime;
    private float clipContain;
    private boolean clipReloading;
    private float angle;


    public AutoGun(UnitModel parent, int amount) {
        super(parent, GunType.AUTOMATIC_GUN, amount);
        makeReload();
        angle = 0;
    }
    
    private void makeReload() {
        if(projectilesAmount > CLIP_SIZE) {
            clipContain = CLIP_SIZE;
        } else {
            clipContain = projectilesAmount; 
        }
        projectilesAmount -= clipContain;
        clipReloading = false;
    }
    
    @Override
    public void update(float time) {
        super.update(time);
        if(clipReloading) {
            if(Math.abs(currentTime - lastFireTime) >= RELOADING_CLIP_TIME) {
                makeReload();
            }
        }
    }

    @Override
    public Projectile fire(UnitMoveDirection direction) {

        if(canFireNow()) {   
            lastFireTime = currentTime;

            if(clipContain > 0) {
                clipContain--;
            }

            if(Math.abs(lashShootTime-currentTime) > 0.18f) {
                MainActivity.resources.playSound("shoot03");
                lashShootTime = currentTime;
            }

            Bullet20Unit bullet = new Bullet20Unit(parent.getX(), parent.getY(), this.parent);
            float subAngle = 0;
            switch(direction) {
            case LEFT:
                subAngle =  180 - parent.getRotation();
                break;
                
            case NONE:
                // None
                break;
                
            case RIGHT:
                subAngle = -parent.getRotation();
                break;
            default:
                break;
            }

            bullet.setAccelerationByAngle(subAngle + angle, SHOT_POWER);
            
            if(clipContain == 0) {
                clipReloading = true;
            }
            
            return bullet;
        }
        return null;
    }

    @Override
    public boolean canFireNow() {
        if(clipReloading == false) {
            if(this.clipContain > 0) {
                if(Math.abs(currentTime - lastFireTime) >= RELOADING_TIME) {
                   return true;
                }
            }
        }
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float value) { this.angle = value; }
}
