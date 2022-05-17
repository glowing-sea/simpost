package com.example.login.DataContainer;

public class PendingTasks {
    private PendingTasks (){}

    private static PendingTasks instance = null;

    private boolean deleteAllMessages;

    public static PendingTasks getInstance() {
        if (instance == null) {
            instance = new PendingTasks();
        }
        return instance;
    }

    public void setDeleteAllMessages(boolean deleteAllMessages) {
        this.deleteAllMessages = deleteAllMessages;
    }

    public boolean isDeleteAllMessages() {
        return deleteAllMessages;
    }
}
