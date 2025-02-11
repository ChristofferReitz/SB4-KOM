package dk.sdu.student.chrei21.collisionsystem;

import dk.sdu.student.chrei21.common.data.Entity;
import dk.sdu.student.chrei21.common.data.GameData;
import dk.sdu.student.chrei21.common.data.World;
import dk.sdu.student.chrei21.common.data.entityparts.LifePart;
import dk.sdu.student.chrei21.common.data.entityparts.PositionPart;
import dk.sdu.student.chrei21.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity hitterEntity : world.getEntities()) {
            for (Entity collidedEntity : world.getEntities()) {
                if (hitterEntity.getID().equals(collidedEntity.getID())) {
                    continue;
                }

                LifePart hitterEntityLifePart = hitterEntity.getPart(LifePart.class);
                LifePart collidedEntityLifePart = hitterEntity.getPart(LifePart.class);

                if (
                        hitterEntityLifePart.getLife() > 0
                                && this.collides(hitterEntity, collidedEntity
                        )) {
                    hitterEntityLifePart.setIsHit(true);
                }
            }
        }
    }

    private boolean collides(Entity hitterEntity, Entity collidedEntity) {
        // Get data for collision detection
        PositionPart hitterPositionPart = hitterEntity.getPart(PositionPart.class);
        PositionPart collidedPositionPart = collidedEntity.getPart(PositionPart.class);

        // Calculate distance between
        float dx = (float) (hitterPositionPart.getX() - collidedPositionPart.getX());
        float dy = (float) (hitterPositionPart.getY() - collidedPositionPart.getY());
        float distanceBetween = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        // Check if distance is less than the two radiuses, meaning that they are hitting each other
        float collisionDistance = hitterEntity.getRadius() + collidedEntity.getRadius();
        return distanceBetween < collisionDistance;
    }
}
