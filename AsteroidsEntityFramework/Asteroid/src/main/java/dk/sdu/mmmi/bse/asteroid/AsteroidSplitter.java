package dk.sdu.mmmi.bse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

import static dk.sdu.mmmi.bse.asteroid.AsteroidType.MEDIUM;
import static dk.sdu.mmmi.bse.asteroid.AsteroidType.SMALL;

public class AsteroidSplitter implements IEntityProcessingService {
    Random random = new Random();
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            Asteroid theAsteroid = (Asteroid) asteroid;
            PositionPart posPart = asteroid.getPart(PositionPart.class);
            SplitterPart splitter = asteroid.getPart(SplitterPart.class);

            if (theAsteroid.getSize().equals("LARGE") && splitter.ShouldSplit()) {
                splitter.setShouldSplit(false);
                Asteroid mediumAsteroidOne = createMediumAsteroid(posPart.getX(), posPart.getY());
                Asteroid mediumAsteroidTwo = createMediumAsteroid(posPart.getX(), posPart.getY());
                world.addEntity(mediumAsteroidOne);
                world.addEntity(mediumAsteroidTwo);
            }

            if (theAsteroid.getSize().equals("MEDIUM") && splitter.ShouldSplit()) {
                splitter.setShouldSplit(false);
                Asteroid smallAsteroidOne = createSmallAsteroid(posPart.getX(), posPart.getY());
                Asteroid smallAsteroidTwo = createSmallAsteroid(posPart.getX(), posPart.getY());
                world.addEntity(smallAsteroidOne);
                world.addEntity(smallAsteroidTwo);
            }
        }
    }

    private Asteroid createSmallAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 13f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(SMALL);
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + random.nextInt(50), y+random.nextInt(50), radians));
        asteroid.add(new LifePart(2, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(5);

        return (Asteroid) asteroid;
    }

    private Asteroid createMediumAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(MEDIUM);

        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + random.nextInt(50), y + random.nextInt(50), radians));
        asteroid.add(new LifePart(4, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(10);

        return (Asteroid) asteroid;
    }
    }

