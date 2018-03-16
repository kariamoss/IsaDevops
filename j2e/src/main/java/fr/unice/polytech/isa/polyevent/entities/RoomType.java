package fr.unice.polytech.isa.polyevent.entities;

public enum RoomType {
    MEETING_ROOM("Meeting room", true),
    MEAL("Meal", false),
    LEARNING_CENTRE("Learning centre", true);

    private String name;
    private boolean haveProjector;

    public String getName() {
        return name;
    }

    public boolean isHaveProjector() {
        return haveProjector;
    }

    RoomType(String name, boolean haveProjector) {
        this.name = name;
        this.haveProjector = haveProjector;
    }
}
