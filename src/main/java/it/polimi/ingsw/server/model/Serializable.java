package it.polimi.ingsw.server.model;

// TODO add javadoc
public abstract class Serializable {
    protected SerializationType serializationType;

    public Class<?> getType() {
        return serializationType.getType();
    }

    public void setSerializationType(SerializationType serializationType) {
        if(this.serializationType == null)
            this.serializationType = serializationType;
    }
}
