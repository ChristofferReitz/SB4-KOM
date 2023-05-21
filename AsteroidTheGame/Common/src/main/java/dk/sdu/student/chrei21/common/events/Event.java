package dk.sdu.student.chrei21.common.events;

import dk.sdu.student.chrei21.common.data.Entity;

import dk.sdu.student.chrei21.common.data.Entity;
import java.io.Serializable;

/**
 *
 * @author REITZ
 */
public class Event implements Serializable{
    private final Entity source;

    public Event(Entity source) {
        this.source = source;
    }

    public Entity getSource() {
        return source;
    }
}
