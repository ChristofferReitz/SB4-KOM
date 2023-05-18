package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class Collision  implements IPostEntityProcessingService {
    private boolean collides(Entity hitterEntity, Entity collideEntity){
        //get data for the collision detection
        PositionPart hitterPositionPart = hitterEntity.getPart(PositionPart.class);
        PositionPart collidePositionPart = collideEntity.getPart(PositionPart.class);
        return collides(
                hitterPositionPart.getY(),
                hitterPositionPart.getX(),
                hitterEntity.getRadius(),
                collidePositionPart.getX(),
                collidePositionPart.getY(),
                collideEntity.getRadius());
    }

    //Check if the distance is less than two radius, meaning that they are hitting each other
    private boolean collides(float hitterX, float hitterY, float hitterR, float collideX, float collideY, float collideR) {
        float dx = hitterX - collideX;
        float dy = hitterY - collideY;
        float distanceBetween = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy,2));
        float collisionDistance = hitterR + collideR;
        return distanceBetween < collisionDistance;
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity hitterEntity : world.getEntities()){
            for (Entity collideEntity : world.getEntities()){
                if (hitterEntity.getID().equals(collideEntity.getID())){
                    continue;
                }
                LifePart hitterEntityLifePart = hitterEntity.getPart(LifePart.class);
                if (hitterEntityLifePart.getLife() > 0 && this.collides(hitterEntity,collideEntity)){
                    hitterEntityLifePart.setIsHit(true);
                }
            }
        }
    }
}
