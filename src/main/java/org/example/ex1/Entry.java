package org.example.ex1;

import org.example.Group;
import org.example.Level;

public class Entry {
private int ID;
private String avgname;
private Group group;
private String description;
private Level level;
private int points;
private String status;

public Entry(int ID, String avgname, Group group, String description, Level level, int points, String status) {
    this.ID = ID;
    this.avgname = avgname;
    this.group = group;
    this.description = description;
    this.level = level;
    this.points = points;
    this.status = status;
}

public int getID() {
    return ID;
}
public String getAvgname() {
    return avgname;
}

public Group getGroup() {
    return group;
}
public String getDescription() {
    return description;
}
public Level getLevel() {
    return level;
}
public int getPoints() {
    return points;
}
public String getStatus() {
    return status;
}

public void setID(int ID) {
    this.ID = ID;
}
public void setAvgname(String avgname) {
    this.avgname = avgname;
}
public void setGroup(Group group) {
    this.group = group;
}
public void setDescription(String description) {
    this.description = description;
}
public void setLevel(Level level) {
    this.level = level;
}
public void setPoints(int points) {
    this.points = points;
}
public void setStatus(String status) {
    this.status = status;
}

public String toString(){
    return "Mission{" +
            "id=" + ID +
            ", avenger name=" + avgname+
            ", group=" + group+
            ", description=" + description+
            ", difficulty level=" + level+
            ", points=" + points+
            ", status=" +status +
            '}';

}
}
