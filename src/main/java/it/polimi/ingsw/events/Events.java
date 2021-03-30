package it.polimi.ingsw.events;

public enum Events {

    TEST1 {
        public void handle() {
            System.out.println("handle Events.TEST1\n");
        }
    },

    TEST2 {
        public void handle() {
            System.out.println("handle Events.TEST2\n");
        }
    };

    abstract void handle();
    }
