package it.polimi.ingsw.Events;

public enum Events {

    TEST1 {

        private boolean handled = false;

        public void handle() {
            handled = true;
        }
    },

    TEST2 {
        private boolean handled = false;

        public void handle() {
            handled = true;
        }
    };

    abstract void handle();
}
